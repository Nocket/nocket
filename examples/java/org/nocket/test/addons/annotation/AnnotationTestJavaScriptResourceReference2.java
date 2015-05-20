package org.nocket.test.addons.annotation;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class AnnotationTestJavaScriptResourceReference2 extends JavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    private static final class Holder {
        private static final AnnotationTestJavaScriptResourceReference2 INSTANCE = new AnnotationTestJavaScriptResourceReference2();
    }

    public static AnnotationTestJavaScriptResourceReference2 instance() {
        return Holder.INSTANCE;
    }

    public AnnotationTestJavaScriptResourceReference2() {
        super(AnnotationTestJavaScriptResourceReference2.class, "annotationTest_2.js");
    }

    public static JavaScriptHeaderItem headerItem() {
        return JavaScriptHeaderItem.forReference(instance());
    }
}
