package org.nocket.gen.page.inject;

import gengui.util.SevereGUIException;

import java.lang.reflect.Field;

import org.apache.wicket.Component;
import org.nocket.gen.page.DMDWebGenPageContext;

public class PageComponentInjection {

    private DMDWebGenPageContext context;

    public PageComponentInjection(DMDWebGenPageContext context) {
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
        Field[] fields = fromClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object content = field.get(context.getPage());
            if (content == null) {
                // try annotation injection
                PageComponent annotation = field.getAnnotation(PageComponent.class);
                if (annotation != null) {
                    String name = annotation.value();
                    Component component = context.getComponentRegistry().getComponent(name);
                    if (component == null) {
                        throw new IllegalStateException("Required component with wicketId \"" + name
                                + "\" not found for injection! Please check the binding.");
                    } else {
                        inject(field, component);
                    }
                } else {
                    // try field name injection
                    String name = field.getName().replace("_", ".");
                    Component component = context.getComponentRegistry().getComponent(name);
                    if (component != null) {
                        inject(field, component);
                    }
                }
            }
        }

        // Rekuriv durch alle geerbten Klassen gehen, einschließlich Object
        // -> beinhaltet keine Eigenschaften und ist Vater von Entity!
        Class<?> superClass = fromClass.getSuperclass();
        if (superClass != null) {
            inject(superClass);
        }
    }

    private void inject(Field field, Component component) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(context.getPage(), component);
    }

}
