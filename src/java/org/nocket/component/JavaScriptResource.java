package org.nocket.component;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE})
@Retention(RUNTIME)
public @interface JavaScriptResource {
    String path();
    Class<?> scope() default void.class;
	String id();
	boolean defer();
	String condition();
	String charset();
}
