package dmdweb.gen.resources;

import java.net.URL;

import org.apache.wicket.core.util.resource.UrlResourceStream;

public class DMDUrlResourceStreamReference extends AbstractDMDResourceStreamReference {
    private final URL url;

    DMDUrlResourceStreamReference(final UrlResourceStream urlResourceStream) {
        url = urlResourceStream.getURL();
        saveResourceStream(urlResourceStream);
    }

    public UrlResourceStream getReference() {
        UrlResourceStream resourceStream = new UrlResourceStream(url);
        restoreResourceStream(resourceStream);
        return resourceStream;
    }
}
