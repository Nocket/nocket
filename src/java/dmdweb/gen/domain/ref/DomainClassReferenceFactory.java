package dmdweb.gen.domain.ref;

import gengui.domain.DomainClassReference;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;

public class DomainClassReferenceFactory implements
        DomainReferenceFactoryI<DomainClassReference> {

    private static final Map<String, DomainClassReference> domainClassReferenceCache =
            new HashMap<String, DomainClassReference>();

    private final WrappedDomainClassReference rootReference;

    public DomainClassReferenceFactory(Class<?> rootDomainClass, boolean runtime) {
        this.rootReference = new WrappedDomainClassReference(
                getDomainClassReference(rootDomainClass, runtime));
    }

    @Override
    public WrappedDomainReferenceI<DomainClassReference> getRootReference() {
        return rootReference;
    }

    @Override
    public WrappedDomainReferenceI<DomainClassReference> newSubReference(
            WrappedDomainReferenceI<DomainClassReference> upper, Method getter) {
        return new WrappedDomainClassReference(new DomainClassReference(
                upper.getRef(), getter));
    }

    public static DomainClassReference getDomainClassReference(Class<?> domainClass, boolean runtime) {
        // Wicket's development mode allows to reload classes at runtime, so in that mode
        // we must not cache any data structures which depend on class structures. The
        // DomainClassReference cache is such a candidate.
        if (!runtime || WebApplication.get().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
            return new DomainClassReference(domainClass);
        }

        synchronized (DomainClassReferenceFactory.class) {
            String domainClassName = domainClass.getName();
            DomainClassReference ref = domainClassReferenceCache.get(domainClassName);
            if (ref == null) {
                ref = new DomainClassReference(domainClass);
                domainClassReferenceCache.put(domainClassName, ref);
            }
            return ref;
        }
    }
}
