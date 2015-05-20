package org.nocket.test.addons.annotation;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;

public class AnnotationTestCssResourceReference2 extends CssResourceReference {
    private static final long serialVersionUID = 1L;

    private static final class Holder {
        private static final AnnotationTestCssResourceReference2 INSTANCE = new AnnotationTestCssResourceReference2();
    }

    public static AnnotationTestCssResourceReference2 instance() {
        return Holder.INSTANCE;
    }

    public AnnotationTestCssResourceReference2() {
        super(AnnotationTestCssResourceReference2.class, "annotationTest_2.css");
    }

    public static JavaScriptHeaderItem headerItem() {
        return JavaScriptHeaderItem.forReference(instance());
    }
}