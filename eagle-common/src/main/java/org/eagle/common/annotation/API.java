package org.eagle.common.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * @description 实际调用的API名称。接口方法上未标记的不予暴露
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface API {

	String value();
}
