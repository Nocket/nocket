package org.nocket.gen.page.element;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class BodyElement extends AbstractNoDomainPageElement<Void> {

    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private Map<String, Object> styleAttributes = new HashMap<String, Object>();

    public BodyElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
        parseStyle(element.attr("style"));
    }

    /**
     * Zerlegt width: 400px; height: 200px
     * 
     * @param attr
     */
    private void parseStyle(String attr) {
        String[] strings = StringUtils.split(attr, ";");

        for (int i = 0; strings != null && i < strings.length; i++) {
            // width: 400px
            String keyValue = strings[i];
            // key = width; value = 400px
            String[] keyValuePair = StringUtils.split(keyValue, ":", 2);

            String key = keyValuePair[0].trim().toLowerCase();
            String value = keyValuePair.length > 1 ? keyValuePair[1] : "";

            // tranformedValue = 400 as Long
            Object tranformedValue = normalizeOrAssertValue(key, value);
            styleAttributes.put(key, tranformedValue);
        }
    }

    protected Object normalizeOrAssertValue(final String key, final String value) {
        String tranformedValue = StringUtils.trim(value);
        if ((WIDTH.equals(key) || HEIGHT.equals(key)) && StringUtils.isNotBlank(tranformedValue)) {
            tranformedValue = StringUtils.lowerCase(tranformedValue);
            tranformedValue = StringUtils.chomp(tranformedValue, "px");
            try {
                Integer intValue = Integer.parseInt(tranformedValue);
                return intValue;
            } catch (Exception e) {
                throw new IllegalArgumentException("Expected a integer value for style attribute " + key
                        + ", but found "
                        + value);
            }
        }
        return tranformedValue;
    }

    @Deprecated
    @Override
    public IModel<Void> getModel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitBody(this);
    }

    public Object getStyleAttributeValue(String attribute) {
        return styleAttributes.get(attribute);
    }

    public Integer getWidth() {
        return (Integer) styleAttributes.get(WIDTH);
    }

    public Integer getHeight() {
        return (Integer) styleAttributes.get(HEIGHT);
    }

}
