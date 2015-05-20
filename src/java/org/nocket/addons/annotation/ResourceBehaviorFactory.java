package org.nocket.addons.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;
import org.nocket.util.SevereWebException;

public class ResourceBehaviorFactory {

	public static Behavior getResourceBehavior(Annotation annotation) {
		if (annotation instanceof CSSResource) {
			return createResourceBehavior((CSSResource) annotation);
		} else if (annotation instanceof CSSResourceRef) {
			return createResourceBehavior((CSSResourceRef) annotation);
		} else if (annotation instanceof JavaScriptResource) {
			return createResourceBehavior((JavaScriptResource) annotation);
		} else if (annotation instanceof JavaScriptResourceRef) {
			return createResourceBehavior((JavaScriptResourceRef) annotation);
		}

		throw new SevereWebException("Unknown annotation = " + annotation);
	}

	public static List<Behavior> createResourceBehaviors(ResourceRefs annotation) {
		List<Behavior> behaviors = new ArrayList<Behavior>();
		for (int i = 0; i < annotation.cssResources().length; i++) {
			behaviors.add(createResourceBehavior(annotation.cssResources()[i]));
		}
		for (int i = 0; i < annotation.cssResourceRefs().length; i++) {
			behaviors.add(createResourceBehavior(annotation.cssResourceRefs()[i]));
		}
		for (int i = 0; i < annotation.javaScriptResources().length; i++) {
			behaviors.add(createResourceBehavior(annotation.javaScriptResources()[i]));
		}
		for (int i = 0; i < annotation.javaScriptResources().length; i++) {
			behaviors.add(createResourceBehavior(annotation.javaScriptResourceRefs()[i]));
		}
		return behaviors;
	}

	public static boolean isResourceAnnotation(Annotation annotation) {
		return annotation instanceof ResourceRefs || //
						annotation instanceof CSSResource || //
						annotation instanceof CSSResourceRef || //
						annotation instanceof JavaScriptResource || //
						annotation instanceof JavaScriptResourceRef;
	}

	private static Behavior createResourceBehavior(CSSResourceRef cssResourceReference) {
		try {
			ResourceReference resourceReference = cssResourceReference.cssResourceClass().newInstance();
			return new CSSResourceRefBehavior(resourceReference);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SevereWebException(e);
		}
	}

	private static Behavior createResourceBehavior(CSSResource cssResource) {
		return new CSSResourceBehavior(cssResource.path(), cssResource.scope());
	}

	private static Behavior createResourceBehavior(JavaScriptResourceRef resourceReference) {
		try {
			return new JavaScriptResourceRefBehavior(resourceReference.javaScriptResourceClass().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SevereWebException(e);
		}
	}

	private static Behavior createResourceBehavior(JavaScriptResource resource) {
		String path = resource.path();
		String id = resource.id();
		boolean defer = resource.defer();
		String condition = resource.condition();
		String charset = resource.charset();
		Class<?> scope = resource.scope();

		return new JavaScriptResourceBehavior(path, id, defer, condition, charset, scope);
	}

	public static class CSSResourceBehavior extends Behavior {
		private static final long serialVersionUID = 1L;
		private final String path;
		private final Class<?> scope;

		public CSSResourceBehavior(String path, Class<?> scope) {
			super();
			this.path = path;
			this.scope = scope;
		}

		public void renderHead(Component component, IHeaderResponse response) {
			HeaderItem headerItem;
			if (scope == void.class || scope == Object.class) {
				headerItem = new CssUrlReferenceHeaderItem(path, null, null);
			} else {
				headerItem = CssHeaderItem.forReference(new CssResourceReference(scope, path));
			}
			response.render(headerItem);
		}
	}

	public static class CSSResourceRefBehavior extends Behavior {
		private static final long serialVersionUID = 1L;
		private final ResourceReference resourceReference;

		public CSSResourceRefBehavior(ResourceReference resourceReference) {
			super();
			this.resourceReference = resourceReference;
		}

		public void renderHead(Component component, IHeaderResponse response) {
			HeaderItem headerItem = CssHeaderItem.forReference(resourceReference);
			response.render(headerItem);
		}
	}

	public static class JavaScriptResourceRefBehavior extends Behavior {
		private static final long serialVersionUID = 1L;
		private final ResourceReference resourceReference;

		public JavaScriptResourceRefBehavior(ResourceReference resourceReference) {
			super();
			this.resourceReference = resourceReference;
		}

		public void renderHead(Component component, IHeaderResponse response) {
			HeaderItem headerItem = JavaScriptHeaderItem.forReference(resourceReference);
			response.render(headerItem);
		}
	}

	public static class JavaScriptResourceBehavior extends Behavior {
		private static final long serialVersionUID = 1L;
		private String id;
		private boolean defer;
		private String condition;
		private String charset;
		private String path;
		private Class<?> scope;

		public JavaScriptResourceBehavior(String path, String id, boolean defer, String condition, String charset, Class<?> scope) {
			super();
			this.path = path;
			this.id = StringUtils.trimToNull(id);
			this.defer = defer;
			this.condition = StringUtils.trimToNull(condition);
			this.charset = StringUtils.trimToNull(charset);
			this.scope = scope;
		}

		public void renderHead(Component component, IHeaderResponse response) {
			JavaScriptHeaderItem headerItem;
			if (scope == void.class || scope == Object.class) {
				headerItem = new JavaScriptUrlReferenceHeaderItem(path, id, defer, charset, condition);
			} else {
				headerItem = JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(scope, path), null, id, defer, charset, condition);
			}
			response.render(headerItem);
		}
	}

	public static List<Annotation> filterAndNormalizeAnnotations(Annotation[] annotations) {
		List<Annotation> filteredAnnotations = new ArrayList<Annotation>();
		for (int i = 0; annotations != null && i < annotations.length; i++) {
			Annotation annotation = annotations[i];
			if (annotation instanceof ResourceRefs) {
				ResourceRefs resourceRefsAnnotation = (ResourceRefs) annotation;
				filteredAnnotations.addAll(filterAndNormalizeAnnotations(resourceRefsAnnotation.cssResources()));
				filteredAnnotations.addAll(filterAndNormalizeAnnotations(resourceRefsAnnotation.cssResourceRefs()));
				filteredAnnotations.addAll(filterAndNormalizeAnnotations(resourceRefsAnnotation.javaScriptResources()));
				filteredAnnotations.addAll(filterAndNormalizeAnnotations(resourceRefsAnnotation.javaScriptResourceRefs()));
			} else if (isResourceAnnotation(annotation)) {
				filteredAnnotations.add(annotation);	
			}
			
		}
		return filteredAnnotations;
	}
	
	public static void addBehavior(Component component, Collection<Annotation> annotations) {
		addBehavior(component, annotations.toArray(new Annotation[0]));
	}
	
	public static void addBehavior(Component component, Annotation[] annotations) {
		List<Annotation> filteredAnnotations = filterAndNormalizeAnnotations(annotations);
		for (Annotation annotation : filteredAnnotations) {
			component.add(getResourceBehavior(annotation));
		}
	}
}
