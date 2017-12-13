package com.nikos.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {

	/**
	 * The lower bound of the version range. Inclusive. Required.
	 * 
	 * @return
	 */
	String from();

	/**
	 * The upper bound of the version range. Exclusive. Optional. If not
	 * specified the upper bound is considered the highest available version.
	 * 
	 * @return
	 */
	String to() default "";
}
