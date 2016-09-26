package cn.org.citycloud.srdz.annotation;


import java.lang.annotation.*;

/**
 * 日志记录注解.
 *
 * @author demon
 * @Date 2016/5/12 14:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface OperationLog {
    String operation() default "";

    int busCode() default 0;
}
