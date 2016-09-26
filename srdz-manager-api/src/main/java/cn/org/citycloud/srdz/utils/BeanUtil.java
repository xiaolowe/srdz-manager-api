package cn.org.citycloud.srdz.utils;

import cn.org.citycloud.srdz.annotation.FieldName;
import cn.org.citycloud.srdz.bean.BeanCompareResultBean;
import cn.org.citycloud.srdz.entity.ServiceCenterLevel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bean工具类.
 *
 * @author demon
 * @Date 2016/5/11 9:40
 */
public class BeanUtil {

    /**
     * 将java bean转到map，map的key为给定的attrs
     *
     * @param obj   bean实例
     * @param clazz bean的class
     * @param attrs 需要转换的属性
     * @return 实体的属性map，key为属性名，value为属性值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> beanToMap(Object obj, Class clazz, String[] attrs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> resultMap = new HashMap<>();
        for (String attr : attrs) {
            Method method = clazz.getMethod(getMethodGetName(attr));
            resultMap.put(attr, method.invoke(obj));
        }
        return resultMap;
    }

    /**
     * list<bean>转成list<Map>
     *
     * @param list
     * @param clazz
     * @param attrs
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T>List<Map<String, Object>> beanListToMapList(List<T> list, Class clazz, String[] attrs) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (T obj : list) {
            resultMap.add(beanToMap(obj, clazz, attrs));
        }
        return resultMap;
    }

    /**
     * bean转为map
     *
     * @param obj
     * @param clazz
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> beanToMap(Object obj, Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> resultMap = new HashMap<>();
        for (Field field : fields) {
            if (getMethodGetName(field.getName()).equalsIgnoreCase("getSerialVersionUID")) {
                continue;
            }
            Method method = clazz.getMethod(getMethodGetName(field.getName()));
            resultMap.put(field.getName(), method.invoke(obj));
        }
        return resultMap;
    }

    /**
     * 组装get方法名
     *
     * @param attrName
     * @return
     */
    public static String getMethodGetName(String attrName) {
        StringBuffer result = new StringBuffer("get");
        result.append(attrName.substring(0, 1).toUpperCase());
        result.append(attrName.substring(1, attrName.length()));
        return result.toString();
    }

    /**
     * 组装set方法名
     *
     * @param attrName
     * @return
     */
    public static String getMethodSetName(String attrName) {
        StringBuffer result = new StringBuffer("set");
        result.append(attrName.substring(0, 1).toUpperCase());
        result.append(attrName.substring(1, attrName.length()));
        return result.toString();
    }

    /**
     * 比较两个bean之间的不同，并返回不同的内容
     *
     * @param oldObj
     * @param newObj
     * @return
     */
    public static Map<String, BeanCompareResultBean> compareObj(Object oldObj, Object newObj) {
        // key保存字段名称，value为前后的值
        Map<String, BeanCompareResultBean> result = new HashMap<>();
        Field[] oldFields = oldObj.getClass().getDeclaredFields();
        Field[] newFields = newObj.getClass().getDeclaredFields();
        List<String> newFieldList = new ArrayList<>();
        for (Field field : newFields) {
            newFieldList.add(field.getName());
        }
        for (Field field : oldFields) {
            if (field.isAnnotationPresent(FieldName.class)) {
                if (getMethodGetName(field.getName()).equalsIgnoreCase("getSerialVersionUID")) {
                    continue;
                }
                try {
                    Method oldMethod = oldObj.getClass().getMethod(getMethodGetName(field.getName()));
                    if (newFieldList.contains(field.getName())) {
                        Method newMethod = newObj.getClass().getMethod(getMethodGetName(field.getName()));
                        Object resultOld = oldMethod.invoke(oldObj);
                        Object resultNew = newMethod.invoke(newObj);
                        if (!resultOld.equals(resultNew)) {
                            BeanCompareResultBean bean = new BeanCompareResultBean(resultOld, resultNew);
                            result.put(field.getAnnotation(FieldName.class).name(), bean);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 复制属性值
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (getMethodGetName(field.getName()).equalsIgnoreCase("getSerialVersionUID")) {
                continue;
            }
            try {
                Method getMethod = source.getClass().getMethod(getMethodGetName(field.getName()));
                Method setMethod = target.getClass().getMethod(getMethodSetName(field.getName()), field.getType());
                setMethod.invoke(target, getMethod.invoke(source));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 获取复制的实体
     *
     * @param obj
     * @return
     */
    public static <T>T getDeepCopy(Object obj) {
        T result = null;
        try {
            result = (T) obj.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        copyProperties(obj, result);
        return result;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        ServiceCenterLevel s = new ServiceCenterLevel();
        s.setServiceCenterLevelId(1);
        s.setServiceCenterLevelName("2");
        Object obj = s.getClass().newInstance();
        System.out.println(obj);
    }
}
