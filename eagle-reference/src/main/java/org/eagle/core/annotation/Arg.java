package org.eagle.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author lhs
 * @Time 2017-04-02 14:55:04
 * @Description API标注的方法中，参数的标注，以便组拼JSON
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, PARAMETER })
public @interface Arg {
	String value();
}
