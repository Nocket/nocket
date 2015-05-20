package org.nocket.addons.annotation;

import gengui.util.SevereGUIException;

import java.lang.reflect.Field;

import org.apache.wicket.Component;
import org.nocket.gen.page.DMDWebGenPageContext;

public class ResourceReferenceInjection {

	private DMDWebGenPageContext context;

	public ResourceReferenceInjection(DMDWebGenPageContext context) {
		this.context = context;
	}

	public void inject() {
		try {
			inject(context.getPage().getClass());
		} catch (Exception e) {
			throw new SevereGUIException(e);
		}
	}

	private void inject(Class<?> fromClass) throws IllegalArgumentException, IllegalAccessException {
		ResourceBehaviorFactory factory = new ResourceBehaviorFactory();

		Field[] fields = fromClass.getDeclaredFields();
		for (Field field : fields) {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			Object content = field.get(context.getPage());
			if (content instanceof Component) {
				Component component = (Component) content;
				factory.addBehavior(component, field.getAnnotations());
			}
			field.setAccessible(accessible);
		}

		// Rekuriv durch alle geerbten Klassen gehen, einschließlich
		// Object
		// -> beinhaltet keine Eigenschaften und ist Vater von Entity!
		Class<?> superClass = fromClass.getSuperclass();
		if (superClass != null) {
			inject(superClass);
		}
	}

}
