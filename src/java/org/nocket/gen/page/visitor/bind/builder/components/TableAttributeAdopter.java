package org.nocket.gen.page.visitor.bind.builder.components;

import org.apache.commons.lang.StringUtils;
import org.nocket.component.table.GenericDataTableConfigurator;
import org.nocket.component.table.TableItemPosition;
import org.nocket.gen.page.element.TableElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableAttributeAdopter {

    final private static Logger log = LoggerFactory.getLogger(TableAttributeAdopter.class);

    private TableItemPosition navigationbarPosition = TableItemPosition.bottom;

    public TableAttributeAdopter(TableElement e) {
        analyseClassAttribute(e);
    }

    private void analyseClassAttribute(TableElement e) {
        String classAttr = e.getElement().attr("class");

        if (StringUtils.isBlank(classAttr)) {
            return;
        }

        String[] attributes = StringUtils.split(classAttr);

        for (String attribute : attributes) {
            if (attribute.toLowerCase().startsWith("table-navbar.")) {
                analyseNavigationbarPosition(attribute, e);
            }
        }
    }

    private void analyseNavigationbarPosition(String attribute, TableElement e) {
        if ("table-navbar.bottom".equals(attribute)) {
            navigationbarPosition = TableItemPosition.bottom;
        } else if ("table-navbar.top".equals(attribute)) {
            navigationbarPosition = TableItemPosition.top;
        } else {
            log.warn("Table with wicketd = '" + e.getWicketId() + "' and unknown TableItemPosition = '" + attribute
                    + "'");
        }

    }

    public void populate(GenericDataTableConfigurator<?> config) {
        config.withNavigationBar(navigationbarPosition);
    }

}
