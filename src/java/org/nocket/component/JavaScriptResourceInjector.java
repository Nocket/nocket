package org.nocket.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.nocket.util.SevereWebException;

public class JavaScriptResourceInjector implements IComponentInstantiationListener {
	
	private final Map<String, RenderHeadBehavior> behaviorMap = new HashMap<>();

	@Override
	public void onInstantiation(Component component) {
		RenderHeadBehavior RenderHeadBehavior = getRenderHeadBehavior(component);
		if (RenderHeadBehavior != null) {
			component.add(RenderHeadBehavior);
		}
	}

	protected RenderHeadBehavior getRenderHeadBehavior(Component component) {
		JavaScriptResource javaScriptResource = component.getClass().getAnnotation(JavaScriptResource.class);
		if (javaScriptResource != null) {
			return getRenderHeadBehavior(javaScriptResource);
		}
		
		JavaScriptResourceRef javaScriptResourceReference = component.getClass().getAnnotation(JavaScriptResourceRef.class);
		if (javaScriptResourceReference != null) {
			return getRenderHeadBehavior(javaScriptResourceReference);
		}
		
		return null;
	}

	private RenderHeadBehavior getRenderHeadBehavior(JavaScriptResourceRef javaScriptResourceReference) {
		String key = javaScriptResourceReference.javaScriptResourceClass().getName();
		
		RenderHeadBehavior RenderHeadBehavior = behaviorMap.get(key);
		if (RenderHeadBehavior == null) {
			RenderHeadBehavior = createBehavior(javaScriptResourceReference);
			behaviorMap.put(key, RenderHeadBehavior);
		}
		
		return RenderHeadBehavior;
	}

	private RenderHeadBehavior getRenderHeadBehavior(JavaScriptResource javaScriptResource) {
		String key = javaScriptResource.path();
		
		RenderHeadBehavior RenderHeadBehavior = behaviorMap.get(key);
		if (RenderHeadBehavior == null) {
			RenderHeadBehavior = createBehavior(javaScriptResource);
			behaviorMap.put(key, RenderHeadBehavior);
		}
		
		return RenderHeadBehavior;
	}

	private RenderHeadBehavior createBehavior(JavaScriptResourceRef javaScriptResourceReference) {
		try {
			HeaderItem headerItem = JavaScriptHeaderItem.forReference(javaScriptResourceReference.javaScriptResourceClass().newInstance());
			return new RenderHeadBehavior(headerItem);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SevereWebException(e);
		}
	}
	
	private RenderHeadBehavior createBehavior(JavaScriptResource javaScriptResource) {
		HeaderItem headerItem;
		String path = javaScriptResource.path();
		String id = javaScriptResource.id();
		boolean defer = javaScriptResource.defer();
		String condition = javaScriptResource.condition();
		String charset = javaScriptResource.charset();

		if (javaScriptResource.scope() == void.class) {
			headerItem = new JavaScriptUrlReferenceHeaderItem(path, id, defer, charset, condition);
		} else {
			headerItem = JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(javaScriptResource.scope(), path), null, id, defer, charset, condition);
		}
		return new RenderHeadBehavior(headerItem);
	}
}
