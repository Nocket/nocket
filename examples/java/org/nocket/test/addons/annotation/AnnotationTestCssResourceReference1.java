package org.nocket.test.addons.annotation;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;

public class AnnotationTestCssResourceReference1 extends CssResourceReference {
    private static final long serialVersionUID = 1L;

    private static final class Holder {
        private static final AnnotationTestCssResourceReference1 INSTANCE = new AnnotationTestCssResourceReference1();
    }

    public static AnnotationTestCssResourceReference1 instance() {
        return Holder.INSTANCE;
    }

    public AnnotationTestCssResourceReference1() {
        super(AnnotationTestCssResourceReference1.class, "annotationTest_1.css");
    }

    public static JavaScriptHeaderItem headerItem() {
        return JavaScriptHeaderItem.forReference(instance());
    }
}