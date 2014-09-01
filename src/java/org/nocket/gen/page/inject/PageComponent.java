package org.nocket.gen.page.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * With this annotation you can mark a field to get the component with the specified wicketId injected into it. Make
 * sure the type is appropriate, or else an exception will be thrown.
 * 
 * It is also advisable to use the generated constants for the wicketId to make this annotation typesafe for
 * refactorings.
 * 
 * Alternatively you can set the name of the field the same as the wicketId with "_" instead of "." and it will get
 * injected aswell, though not required. If the name does not match exactly, the field will stay null.
 * 
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PageComponent {
    String value();
}
