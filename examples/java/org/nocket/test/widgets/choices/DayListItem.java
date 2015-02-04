package org.nocket.test.widgets.choices;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.util.io.IClusterable;

@SuppressWarnings("serial")
public class DayListItem implements IClusterable {

    private String listName;

    public DayListItem(String listName) {
        super();
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    @Override
    public String toString() {
        return listName;
    }

    public final static class ChoiceRenderer implements IChoiceRenderer<DayListItem> {

        @Override
        public Object getDisplayValue(DayListItem object) {
            return object.getListName();
        }

        @Override
        public String getIdValue(DayListItem object, int index) {
            return object.getListName();
        }

    }

}