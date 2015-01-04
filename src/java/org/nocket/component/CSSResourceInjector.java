package org.nocket.component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.nocket.util.SevereWebException;

public class CSSResourceInjector implements IComponentInstantiationListener {
	
	private final Map<String, Behavior> behaviorMap = new HashMap<>();

	@Override
	public void onInstantiation(Component component) {
		Behavior[] resourceBehaviors = getResourceBehavior(component);
		if (resourceBehaviors != null && resourceBehaviors.length > 0) {
			component.add(resourceBehaviors);
		}
	}

	protected Behavior[] getResourceBehavior(Component component) {
		List<? extends Annotation> annotations = getAnnotations(component, CSSResource.class, CSSResourceRef.class);

		List<Behavior> behaviors = new ArrayList<Behavior>();

		for (Annotation annotation : annotations) {
			if (annotation instanceof CSSResource) {
				behaviors.add(getResourceBehavior((CSSResource) annotation));
			} else 
			if (annotation instanceof CSSResourceRef) {
				behaviors.add(getResourceBehavior((CSSResourceRef) annotation));
			} else {
				throw new SevereWebException("Unknown annotation = " + annotation);
			}
		}
		
		return behaviors.toArray(new CSSResourceBehavior[0]);
	}

	private List<? extends Annotation> getAnnotations(Component component, Class<? extends Annotation>... annotationClasses) {
		return getAnnotations(component.getClass(), annotationClasses);
	}
	
	private List<? extends Annotation> getAnnotations(Class<?> clazz, Class<? extends Annotation>... annotationClasses) {
		List result = new ArrayList<>();
		for (Class<? extends Annotation> annotationClass : annotationClasses) {
			Annotation annotation = clazz.getAnnotation(annotationClass);
			if (annotation != null) {
				result.add(annotation);
			}
		}
		
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null && !Object.class.equals(superclass)) {
			List<?> annotations = getAnnotations(superclass, annotationClasses);
			result.addAll(annotations);
		}

		Class<?>[] interfaces = clazz.getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			result.addAll(getAnnotations(interfaces[i], annotationClasses));
		}
		return result;
	}

	private Behavior getResourceBehavior(CSSResourceRef cssResourceReference) {
		String key = cssResourceReference.cssResourceClass().getName();
		
		Behavior resourceBehavior = behaviorMap.get(key);
		if (resourceBehavior == null) {
			resourceBehavior = createBehavior(cssResourceReference);
			behaviorMap.put(key, resourceBehavior);
		}
		
		return resourceBehavior;
	}

	private Behavior getResourceBehavior(CSSResource cssResource) {
		String key = cssResource.path();
		
		Behavior resourceBehavior = behaviorMap.get(key);
		if (resourceBehavior == null) {
			resourceBehavior = createBehavior(cssResource);
			behaviorMap.put(key, resourceBehavior);
		}
		
		return resourceBehavior;
	}

	private Behavior createBehavior(CSSResourceRef cssResourceRef) {
		return new CSSResourceRefBehavior(cssResourceRef);
	}
	
	private Behavior createBehavior(CSSResource cssResource) {
		return new CSSResourceBehavior(cssResource);
	}

	public static class CSSResourceBehavior extends Behavior {
		private static final long serialVersionUID = 1L;
		private CSSResource cssResource;

		public CSSResourceBehavior(CSSResource cssResource) {
			super();
			this.cssResource = cssResource;
		}

		public void renderHead(Component component, IHeaderResponse response) {
			HeaderItem headerItem;
			if (cssResource.scope() == Object.class) {
				headerItem = new CssUrlReferenceHeaderItem(cssResource.path(), null, null);
			} else {
				headerItem = CssHeaderItem.forReference(new CssResourceReference(cssResource.scope(), cssResource.path()));
			}
			response.render(headerItem);
		}
	}
	
	public static class CSSResourceRefBehavior extends Behavior {
		private static final long serialVersionUID = 1L;
		private CSSResourceRef cssResourceRef;
		
		public CSSResourceRefBehavior(CSSResourceRef cssResourceRef) {
			super();
			this.cssResourceRef = cssResourceRef;
		}
		
		public void renderHead(Component component, IHeaderResponse response) {
			try {
				HeaderItem headerItem = CssHeaderItem.forReference(cssResourceRef.cssResourceClass().newInstance());
				response.render(headerItem);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new SevereWebException(e);
			}
		}
	}
}
