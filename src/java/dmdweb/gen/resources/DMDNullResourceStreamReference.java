package dmdweb.gen.resources;

import org.apache.wicket.util.resource.IResourceStream;

/**
 * A singleton reference that is used for resource streams which do not exists.
 * I.e. if there is a key in the cache which value is
 * NullResourceStreamReference.INSTANCE then there is no need to lookup again
 * for this key anymore.
 */
public class DMDNullResourceStreamReference implements DMDResourceStreamReferenceI {
    final static DMDNullResourceStreamReference INSTANCE = new DMDNullResourceStreamReference();

    public IResourceStream getReference() {
        return null;
    }
}