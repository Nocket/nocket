package dmdweb.gen.domain.ref;

import gengui.domain.DomainClassReference;
import gengui.domain.DomainObjectReference;

import java.lang.reflect.Method;

public class DomainObjectReferenceFactory implements
        DomainReferenceFactoryI<DomainObjectReference> {

    private final WrappedDomainObjectReference rootReference;

    public DomainObjectReferenceFactory(Object rootDomainObject) {
        this.rootReference = new WrappedDomainObjectReference(
                new DomainObjectReference(rootDomainObject),
                DomainClassReferenceFactory.getDomainClassReference(rootDomainObject.getClass(), true));
    }

    @Override
    public WrappedDomainReferenceI<DomainObjectReference> getRootReference() {
        return rootReference;
    }

    @Override
    public WrappedDomainReferenceI<DomainObjectReference> newSubReference(
            WrappedDomainReferenceI<DomainObjectReference> upper, Method getter) {
        return new WrappedDomainObjectReference(new DomainObjectReference(
                upper.getRef(), getter), new DomainClassReference(
                upper.getClassRef(), getter));
    }

}
