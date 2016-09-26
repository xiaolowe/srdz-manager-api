package cn.org.citycloud.srdz.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * websocket消息处理器
 *
 * 在需要发信的地方注入这个handler,
 * 通过handler调用相应的broadcast或者sendToUser方法即可
 *
 * @author demon
 */
@Component
public class WebSocketMsgsHandler implements WebSocketHandler {
    public static final Map<Integer, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<Integer, WebSocketSession>();
    }

    /**
     * 建立连接后
     */
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        Integer uid = (Integer) session.getAttributes().get("uid");
        if (userSocketSessionMap.get(uid) == null) {
            userSocketSessionMap.put(uid, session);
        }
    }

    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 如果要实现聊天需要重写这个方法，站内信不需要，因为这个方法是接收用户信息的，然后再转发干嘛的
    }

    /**
     * 消息传输错误处理
     */
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
        // 移除Socket会话
        removeSession(it, session);
    }

    /**
     * 关闭连接后
     */
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        System.out.println("Websocket:" + session.getId() + "已经关闭");
        Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
        // 移除Socket会话
        removeSession(it, session);
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     * @throws IOException
     */
    public void broadcast(final TextMessage message) throws IOException {
        Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();

        // 多线程群发
        while (it.hasNext()) {

            final Entry<Integer, WebSocketSession> entry = it.next();

            if (entry.getValue().isOpen()) {
                // entry.getValue().sendMessage(message);
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            if (entry.getValue().isOpen()) {
                                entry.getValue().sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();
            }

        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(Integer uid, TextMessage message)
            throws IOException {
        WebSocketSession session = userSocketSessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }


    /**
     * 移除会话
     *
     * @param it
     * @param session
     */
    public void removeSession(Iterator<Entry<Integer, WebSocketSession>> it, WebSocketSession session) {
        while (it.hasNext()) {
            Entry<Integer, WebSocketSession> entry = it.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
                break;
            }
        }
    }

}
