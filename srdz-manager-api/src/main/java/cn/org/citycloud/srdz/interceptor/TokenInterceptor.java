package cn.org.citycloud.srdz.interceptor;

import cn.org.citycloud.srdz.bean.ErrorResponse;
import cn.org.citycloud.srdz.bean.UserToken;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.core.BaseController;
import cn.org.citycloud.srdz.utils.StringUtils;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;





public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	MemcachedClient memcachedClient;
	
	/** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     *  
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 在这里验证Token
		System.out.println("TokenInterceptor preHandle------------" + request.getRequestURI());
		String token = request.getHeader("token");
		String[] downUrls = {"/downExcel", "/orderExcel", "/downInvoledList", "/downCash", "/downPay"};
		if (StringUtils.contains(request.getRequestURI(), downUrls)) {
			token = request.getParameter("token");
		}
		if(token == null||"".equals(token.trim())){

			// 获取OutputStream输出流
			response.setStatus(403);
			responseOutWithJson(response, new ErrorResponse("101", "token无效"));
			return false;
		}

		Object tokenFromCached = memcachedClient.get(token);

		if(tokenFromCached == null) {
			// 获取OutputStream输出流
			response.setStatus(403);
			responseOutWithJson(response, new ErrorResponse("101", "token不存在或过期"));
			return false;
		}

		UserToken tk = (UserToken)tokenFromCached;
		Constants.USER_TOKEN = tk;
		if (HandlerMethod.class.equals(handler.getClass())) {
			// 获取controller，判断是不是Token控制器
			HandlerMethod method = (HandlerMethod) handler;
			Object controller = method.getBean();

			// 判断是否为控制器实现类
			if (controller instanceof BaseController) {
				// 验证Token中信息并注入到Controller中
				BaseController apiController = (BaseController) controller;
				apiController.setToken(token);
				apiController.setUserToken(tk);
				apiController.setUserId(tk.getUserId());
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	/**
	 * 以JSON格式输出
	 * @param response
	 */
	protected void responseOutWithJson(HttpServletResponse response,
			Object responseObject) {
		//将实体对象转换为JSON Object转换
		JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
