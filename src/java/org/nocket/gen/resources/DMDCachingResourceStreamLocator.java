package org.nocket.gen.resources;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.wicket.core.util.resource.UrlResourceStream;
import org.apache.wicket.core.util.resource.locator.IResourceNameIterator;
import org.apache.wicket.core.util.resource.locator.IResourceStreamLocator;
import org.apache.wicket.request.resource.ResourceReference.Key;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * This resource stream locator is mainly a copy-paste-construct from Wicket's
 * CachingResourceStreamLocator. As usual, Wicket defines a lot of stuff final,
 * package-visible, and private, so that we can't simply derive from
 * CachingResourceStreamLocator.<br>
 * The only difference is a function which allows to remove a stream reference
 * in order to re-locate it after having created the resource on the fly.
 * 
 * @author less02
 */
public class DMDCachingResourceStreamLocator implements IResourceStreamLocator {

    private final ConcurrentMap<Key, DMDResourceStreamReferenceI> cache;

    private final IResourceStreamLocator delegate;

    /**
     * Construct.
     * 
     * @param resourceStreamLocator
     *            the delegate
     */
    public DMDCachingResourceStreamLocator(final IResourceStreamLocator resourceStreamLocator) {
        Args.notNull(resourceStreamLocator, "resourceStreamLocator");

        delegate = resourceStreamLocator;

        cache = new ConcurrentHashMap<Key, DMDResourceStreamReferenceI>();
    }

    /**
     * {@inheritDoc}
     * 
     * Checks for {@link DMDResourceStreamReferenceI} in the cache and returns
     * <code>null</code> if the result is
     * {@link DMDNullResourceStreamReference#INSTANCE}, or
     * {@link FileResourceStream} / {@link UrlResourceStream} if there is an
     * entry in the cache. Otherwise asks the delegate to find one and puts it
     * in the cache.
     */
    public IResourceStream locate(Class<?> clazz, String path) {
        Key key = new Key(clazz.getName(), path, null, null, null);
        DMDResourceStreamReferenceI resourceStreamReference = cache.get(key);

        final IResourceStream result;
        if (resourceStreamReference == null) {
            result = delegate.locate(clazz, path);

            updateCache(key, result);
        } else {
            result = resourceStreamReference.getReference();
        }

        return result;
    }

    /**
     * This is the only new thing in here which is not present in Wicket's
     * CachingResourceStreamLocator class. The key is explicitely removed from
     * the cache if already present, before calling
     * {@link #locate(Class, String)} to force a re-allocation of the resource.
     */
    public IResourceStream relocate(Class<?> clazz, String path) {
        Key key = new Key(clazz.getName(), path, null, null, null);
        cache.remove(key);
        return locate(clazz, path);
    }

    private void updateCache(Key key, IResourceStream stream) {
        if (null == stream) {
            cache.put(key, DMDNullResourceStreamReference.INSTANCE);
        } else if (stream instanceof FileResourceStream) {
            FileResourceStream fileResourceStream = (FileResourceStream) stream;
            cache.put(key, new DMDFileResourceStreamReference(fileResourceStream));
        } else if (stream instanceof UrlResourceStream) {
            UrlResourceStream urlResourceStream = (UrlResourceStream) stream;
            cache.put(key, new DMDUrlResourceStreamReference(urlResourceStream));
        }
    }

    public IResourceStream locate(Class<?> scope, String path, String style, String variation,
            Locale locale, String extension, boolean strict) {
        Key key = new Key(scope.getName(), path, locale, style, variation);
        DMDResourceStreamReferenceI resourceStreamReference = cache.get(key);

        final IResourceStream result;
        if (resourceStreamReference == null) {
            result = delegate.locate(scope, path, style, variation, locale, extension, strict);

            updateCache(key, result);
        } else {
            result = resourceStreamReference.getReference();
        }

        return result;
    }

    public IResourceNameIterator newResourceNameIterator(String path, Locale locale, String style,
            String variation, String extension, boolean strict) {
        return delegate.newResourceNameIterator(path, locale, style, variation, extension, strict);
    }

    public void drop(Class<?> scope, String... keyStrings) {
        for (String keyString : keyStrings) {
            Key key = new Key(scope.getName(), keyString, null, null, null);
            cache.remove(key);
        }
    }

}
