package dmdweb.component.table.columns;

import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;

import dmdweb.component.table.behavior.ClickAjaxEventBehavior;

/**
 * Column adds check-box per table item. Information about the checked state is
 * hold in the Set. Clicks are submitted immediately via Ajax on the model.
 * 
 * @author blaz02
 * 
 * @param <T>
 *            Model object type following Java-Bean convention.
 */
@SuppressWarnings("serial")
public class AjaxSetCheckBoxColum<T> extends SetCheckBoxColum<T> {
    protected final String selectCheckboxJS = "var val=$(this).attr('checked'); $('." + uuid
            + "').each(function() { if($(this).attr('checked') !== val) { $(this).click(); }});";

    public AjaxSetCheckBoxColum(IModel<String> displayModel, Set<T> selected) {
        super(displayModel, selected);
    }

    protected Behavior getTableHeaderCheckboxBehavior() {
        return new Behavior() {
            public void onComponentTag(Component component, ComponentTag
                    tag) {
                tag.put("onclick", selectCheckboxJS);
            }
        };
    }

    protected CheckBox newCheckBox(String id, final IModel<Boolean> checkModel) {
        final CheckBox checkBox = new CheckBox("check", checkModel) {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                if (checkModel.getObject() != null) {
                    tag.append("class", uuid, " ");
                }
            }
        };
        if (checkModel.getObject() != null) { // checkModel is null for the "select all" checkbox in the column header
            checkBox.add(new ClickAjaxEventBehavior<Boolean>(checkModel) {
                protected void onEvent(AjaxRequestTarget target, IModel<Boolean> model) {
                    AbstractCheckBoxModel m = (AbstractCheckBoxModel) model;
                    if (m.isSelected()) {
                        m.unselect();
                    } else {
                        m.select();
                    }
                    target.add(checkBox);
                }
            });
        }
        return checkBox;
    }

}
