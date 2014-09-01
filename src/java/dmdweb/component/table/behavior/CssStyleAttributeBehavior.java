package dmdweb.component.table.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;

public abstract class CssStyleAttributeBehavior extends Behavior {

    private static final long serialVersionUID = 1L;

    protected abstract String getColumnCssStyleAttribute();

    /**
     * @see Behavior#onComponentTag(Component, ComponentTag)
     */
    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        String columStyle = getColumnCssStyleAttribute();
        if (!Strings.isEmpty(columStyle)) {
            tag.put("style", columStyle);
        }
    }
}
