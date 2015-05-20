package org.nocket.test.addons.annotation;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class AnnotationTestJavaScriptResourceReference1 extends JavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    private static final class Holder {
        private static final AnnotationTestJavaScriptResourceReference1 INSTANCE = new AnnotationTestJavaScriptResourceReference1();
    }

    public static AnnotationTestJavaScriptResourceReference1 instance() {
        return Holder.INSTANCE;
    }

    public AnnotationTestJavaScriptResourceReference1() {
        super(AnnotationTestJavaScriptResourceReference1.class, "annotationTest_1.js");
    }

    public static JavaScriptHeaderItem headerItem() {
        return JavaScriptHeaderItem.forReference(instance());
    }
}