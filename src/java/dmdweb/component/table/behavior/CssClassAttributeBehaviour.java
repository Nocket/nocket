package dmdweb.component.table.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;

public abstract class CssClassAttributeBehaviour extends Behavior {

    private static final long serialVersionUID = 1L;

    protected abstract String getCssClass();

    /**
     * @see Behavior#onComponentTag(Component, ComponentTag)
     */
    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        String className = getCssClass();
        if (!Strings.isEmpty(className)) {
            CharSequence oldClassName = tag.getAttribute("class");
            if (Strings.isEmpty(oldClassName)) {
                tag.put("class", className);
            } else {
                tag.put("class", oldClassName + " " + className);
            }
        }
    }
}