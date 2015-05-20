package org.nocket.addons.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE, FIELD})
@Retention(RUNTIME)
public @interface JavaScriptResource {
    String path();
    Class<?> scope() default Object.class;
	String id() default "";
	boolean defer() default false;
	String condition() default "";
	String charset() default "";
}
