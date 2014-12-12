package org.nocket.component.table.columns;

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

// TODO: Auto-generated Javadoc
/**
 * The Class CheckBoxColumn.
 *
 * @param <T> the generic type
 */
public abstract class CheckBoxColumn<T> extends DMDAbstractColumn<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The uuid. */
    protected final String uuid = UUID.randomUUID().toString().
            replace("-", "");
    
    /** The select checkbox js. */
    protected final String selectCheckboxJS = "var val=$(this).attr('checked'); $('."
            + uuid
            + "').each(function() { if( 'checked' == val ) { $(this).attr('checked', val);} else {$(this).removeAttr('checked');} });";

    /**
     * Instantiates a new check box column.
     *
     * @param displayModel the display model
     */
    public CheckBoxColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
     */
    public void populateItem(Item<ICellPopulator<T>> cellItem,
            String componentId, IModel<T> rowModel) {
        cellItem.add(new CheckPanel(componentId, newCheckBoxModel(rowModel)));
    }

    /**
     * New check box.
     *
     * @param id the id
     * @param checkModel the check model
     * @return the check box
     */
    protected CheckBox newCheckBox(String id, IModel<Boolean> checkModel) {
        return new CheckBox("check", checkModel) {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.append("class", uuid, " ");
            }
        };
    }

    /**
     * New check box model.
     *
     * @param rowModel the row model
     * @return the i model
     */
    protected abstract IModel<Boolean> newCheckBoxModel(IModel<T> rowModel);

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#getHeader(java.lang.String)
     */
    public Component getHeader(String componentId) {
        CheckPanel panel = new CheckPanel(componentId, new
                Model<Boolean>());
        panel.get("check").add(getTableHeaderCheckboxBehavior());
        return panel;
    }

    /**
     * Gets the table header checkbox behavior.
     *
     * @return the table header checkbox behavior
     */
    protected Behavior getTableHeaderCheckboxBehavior() {
        return new Behavior() {
            public void onComponentTag(Component component, ComponentTag
                    tag) {
                tag.put("onclick", selectCheckboxJS);
            }
        };
    }

    /**
     * The Class CheckPanel.
     */
    private class CheckPanel extends Panel {
        
        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /**
         * Instantiates a new check panel.
         *
         * @param id the id
         * @param checkModel the check model
         */
        public CheckPanel(String id, IModel<Boolean> checkModel) {
            super(id);
            add(newCheckBox("check", checkModel));
        }
    }
}