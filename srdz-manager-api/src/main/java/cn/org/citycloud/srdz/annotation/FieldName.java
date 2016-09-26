package cn.org.citycloud.srdz.annotation;

import java.lang.annotation.*;

/**
 * 用于标识属性名.
 *
 * @author demon
 * @Date 2016/5/14 12:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface FieldName {
    String name() default "";
}
