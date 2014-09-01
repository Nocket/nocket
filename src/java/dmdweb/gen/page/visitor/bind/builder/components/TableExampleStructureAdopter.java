package dmdweb.gen.page.visitor.bind.builder.components;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dmdweb.component.table.ColumnSortOrder;
import dmdweb.component.table.GenericDataTableConfigurator;
import dmdweb.gen.domain.visitor.html.AbstractHtmlComponentBuilder;
import dmdweb.gen.page.element.TableElement;

/**
 * This class analyses a TableElement for the presence of an example table
 * header which it may extract hand-modified details from to adopt them in
 * Wicket´s dynamically created table component.<br>
 * As example content tends to by out of date, the class is designed relatively
 * tolerant against content which does not fit the domain class' structure any
 * more.
 * 
 * @author less02
 */
public class TableExampleStructureAdopter {

    public static final String ATTR_ID = AbstractHtmlComponentBuilder.ATTR_ID;
    public static final String ATTR_CLASS = AbstractHtmlComponentBuilder.ATTR_CLASS;
    public static final String ATTR_STYLE = AbstractHtmlComponentBuilder.ATTR_STYLE;
    public static final String ATTR_VALUE_ACTIONCOLUMN = AbstractHtmlComponentBuilder.ATTR_VALUE_ACTIONCOLUMN;
    public static final String ATTR_VALUE_WICKET_ORDER_UP = AbstractHtmlComponentBuilder.ATTR_VALUE_WICKET_ORDER_UP;
    public static final String ATTR_VALUE_WICKET_ORDER_NONE = AbstractHtmlComponentBuilder.ATTR_VALUE_WICKET_ORDER_NONE;
    public static final String ATTR_VALUE_WICKET_ORDER_DOWN = AbstractHtmlComponentBuilder.ATTR_VALUE_WICKET_ORDER_DOWN;

    protected TableElement e;
    protected Elements dataColumns;
    protected Elements buttonColumns;

    public TableExampleStructureAdopter(TableElement e) {
        this.e = e;
        Element headerRow = findFirstChildByTag(findFirstChildByTag(e.getElement(), "thead"), "tr");
        if (headerRow != null) {
            dataColumns = new Elements();
            buttonColumns = new Elements();
            Iterator<Element> headerIterator = headerRow.getElementsByTag("th").iterator();
            while (headerIterator.hasNext()) {
                Element header = headerIterator.next();
                if (header.hasClass(ATTR_VALUE_ACTIONCOLUMN)) {
                    buttonColumns.add(header);
                } else {
                    dataColumns.add(header);
                }
            }
        }
    }

    protected Element findFirstChildByTag(Element parent, String tag) {
        if (parent == null)
            return null;
        Elements children = parent.getElementsByTag(tag);
        if (children.size() == 0)
            return null;
        return children.get(0);
    }

    /**
     * The table is supposed to be initially sorted in ascending order if there
     * is a data column header present which has the class wicket_orderUp
     * attached or if there is know header present at all. If there is a class
     * wicket_orderDon present, the initial sorting is descending. If neither
     * class is present, the method returns null, assuming that the content is
     * presorted by the application and should not initially be sorted.
     */
    public ColumnSortOrder initialSortOrder() {
        if (dataColumns == null)
            return ColumnSortOrder.UP;
        for (Element column : dataColumns.toArray(new Element[0])) {
            String classAttribute = column.attr(ATTR_CLASS);
            if (classAttribute != null) {
                if (classAttribute.contains(ATTR_VALUE_WICKET_ORDER_UP))
                    return ColumnSortOrder.UP;
                if (classAttribute.contains(ATTR_VALUE_WICKET_ORDER_DOWN))
                    return ColumnSortOrder.DOWN;
            }
        }
        return ColumnSortOrder.NONE;
    }

    public String initialSortColumn() {
        if (dataColumns == null)
            return null;
        Iterator<Element> iter = dataColumns.iterator();
        for (int i = 0; iter.hasNext(); i++) {
            Element element = iter.next();
            String classAttribute = element.attr(ATTR_CLASS);
            if (classAttribute != null) {
                if (classAttribute.contains(ATTR_VALUE_WICKET_ORDER_UP)
                        || classAttribute.contains(ATTR_VALUE_WICKET_ORDER_DOWN))
                    return StringUtils.uncapitalize(element.attr(ATTR_ID));
            }
        }
        return null;
    }

    public void populate(GenericDataTableConfigurator<?> config) {
        config.withInitialSortOrder(initialSortOrder());
        config.withInitialSortColumn(initialSortColumn());
    }

    public String findStyleForColumn(String columnName) {
        String res = iterate(dataColumns, StringUtils.uncapitalize(columnName));
        return res != null ? res : iterate(buttonColumns, StringUtils.uncapitalize(columnName));
    }

    private String iterate(Elements elements, String columnName) {
        if (elements == null) {
            return null;
        }
        Iterator<Element> iter = elements.iterator();
        for (int i = 0; iter.hasNext(); i++) {
            Element element = iter.next();
            if (columnName.equals(StringUtils.uncapitalize(element.attr(ATTR_ID)))) {
                return element.attr(ATTR_STYLE);
            }
        }
        return null;
    }

}
