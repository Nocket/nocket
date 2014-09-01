package dmdweb.gen.page.visitor.bind.builder.components;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dmdweb.component.table.GenericDataTableConfigurator;
import dmdweb.component.table.TableItemPosition;
import dmdweb.gen.page.element.TableElement;

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
