package dmdweb.component.table.columns;

import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class CheckBoxColumn<T> extends DMDAbstractColumn<T> {
    private static final long serialVersionUID = 1L;
    protected final String uuid = UUID.randomUUID().toString().
            replace("-", "");
    protected final String selectCheckboxJS = "var val=$(this).attr('checked'); $('."
            + uuid
            + "').each(function() { if( 'checked' == val ) { $(this).attr('checked', val);} else {$(this).removeAttr('checked');} });";

    public CheckBoxColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    public void populateItem(Item<ICellPopulator<T>> cellItem,
            String componentId, IModel<T> rowModel) {
        cellItem.add(new CheckPanel(componentId, newCheckBoxModel(rowModel)));
    }

    protected CheckBox newCheckBox(String id, IModel<Boolean> checkModel) {
        return new CheckBox("check", checkModel) {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.append("class", uuid, " ");
            }
        };
    }

    protected abstract IModel<Boolean> newCheckBoxModel(IModel<T> rowModel);

    public Component getHeader(String componentId) {
        CheckPanel panel = new CheckPanel(componentId, new
                Model<Boolean>());
        panel.get("check").add(getTableHeaderCheckboxBehavior());
        return panel;
    }

    protected Behavior getTableHeaderCheckboxBehavior() {
        return new Behavior() {
            public void onComponentTag(Component component, ComponentTag
                    tag) {
                tag.put("onclick", selectCheckboxJS);
            }
        };
    }

    private class CheckPanel extends Panel {
        private static final long serialVersionUID = 1L;

        public CheckPanel(String id, IModel<Boolean> checkModel) {
            super(id);
            add(newCheckBox("check", checkModel));
        }
    }
}