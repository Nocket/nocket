package org.nocket.gen.page.element;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.synchronizer.BooleanChoiceRenderer;
import org.nocket.gen.page.element.synchronizer.EnumChoicesRenderer;
import org.nocket.gen.page.element.synchronizer.GeneratedChoicesModel;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class SelectElement extends AbstractDomainPageElement<Object> {

    public static final String SELECTIONTYPE_ATTRIBUTE = "multiple";
    public static final String MULTISELECT_VALURE = "multiple";
    Integer numberOfVisibleEntries;

    public SelectElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
        String sizeAttr = element.attr("size");
        if (StringUtils.isNotBlank(sizeAttr)) {
            try {
                numberOfVisibleEntries = Integer.parseInt(sizeAttr.trim());
            } catch (NumberFormatException nfx) {
                System.err.println("Ignored illegal number format in selection size attribute: " + sizeAttr);
            }
        }
    }

    public Integer getNumberOfVisibleEntries() {
        return numberOfVisibleEntries;
    }

    @Override
    public IModel<Object> innerGetModel() {
        return new PropertyModel<Object>(getPropertyModelObject(), getPropertyExpression());
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitSelect(this);
    }

    public IModel<List<Object>> getChoicesModel() {
        return new GeneratedChoicesModel(this);
    }

    public boolean isMultiselect() {
        return getElement().attr(SELECTIONTYPE_ATTRIBUTE).equals(MULTISELECT_VALURE);
    }

    public IChoiceRenderer getChoicesRenderer() {
        Method method = getDomainElement().getMethod();
        Class<?> returnType = method.getReturnType();
        if (returnType.isEnum()) {
            return new EnumChoicesRenderer(getContext());
        } else if (SynchronizerHelper.isBooleanType(method)) {
            return new BooleanChoiceRenderer(this);
        } else {
            return new ChoiceRenderer();
        }
    }

}
