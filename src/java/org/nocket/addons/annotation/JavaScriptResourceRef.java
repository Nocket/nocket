package org.nocket.addons.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.wicket.request.resource.ResourceReference;

@Target({TYPE,FIELD})
@Retention(RUNTIME)
public @interface JavaScriptResourceRef {
	Class<? extends ResourceReference> javaScriptResourceClass();
}
