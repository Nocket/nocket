package org.nocket.component;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.wicket.request.resource.ResourceReference;

@Target({TYPE})
@Retention(RUNTIME)
public @interface CSSResourceRef {
	Class<? extends ResourceReference> cssResourceClass();
}
