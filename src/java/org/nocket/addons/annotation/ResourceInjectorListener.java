package org.nocket.addons.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;

public class ResourceInjectorListener implements IComponentInstantiationListener {

	@Override
	public void onInstantiation(Component component) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		retrieveAnnotations(component.getClass(), annotations);
		ResourceBehaviorFactory.addBehavior(component, annotations);
	}

	private void retrieveAnnotations(Class<?> clazz, List<Annotation> allAnnotations) {
		Annotation[] claszzAnnotations = clazz.getAnnotations();
		for (int i = 0; claszzAnnotations != null && i < claszzAnnotations.length; i++) {
			if (ResourceBehaviorFactory.isResourceAnnotation(claszzAnnotations[i])) {
				allAnnotations.add(claszzAnnotations[i]);
			}
		}

		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null && !Object.class.equals(superclass)) {
			retrieveAnnotations(superclass, allAnnotations);
		}

		Class<?>[] interfaces = clazz.getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			retrieveAnnotations(interfaces[i], allAnnotations);
		}
	}
}
