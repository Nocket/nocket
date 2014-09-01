package org.nocket.gen.resources;

import java.util.Locale;

import org.apache.wicket.util.resource.IResourceStream;

abstract public class AbstractDMDResourceStreamReference implements DMDResourceStreamReferenceI {
    private String style;

    private Locale locale;

    private String variation;

    protected void saveResourceStream(final IResourceStream resourceStream) {
        style = resourceStream.getStyle();
        locale = resourceStream.getLocale();
        variation = resourceStream.getVariation();
    }

    protected void restoreResourceStream(final IResourceStream resourceStream) {
        resourceStream.setStyle(style);
        resourceStream.setLocale(locale);
        resourceStream.setVariation(variation);
    }
}
