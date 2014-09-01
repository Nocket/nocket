package dmdweb.gen.domain.ref;

import gengui.domain.DomainClassReference;
import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ConnectionReuse;

public class WrappedDomainObjectReference implements
        WrappedDomainReferenceI<DomainObjectReference> {

    private final DomainObjectReference ref;
    private final DomainClassReference classRef;

    public WrappedDomainObjectReference(DomainObjectReference ref,
            DomainClassReference classRef) {
        this.ref = ref;
        this.classRef = classRef;
    }

    @Override
    public DomainObjectReference getRef() {
        return ref;
    }

    @Override
    public DomainClassReference getClassRef() {
        return classRef;
    }

    @Override
    public WrappedDomainReferenceI<DomainObjectReference> replicate(ConnectionReuse reuse) {
        DomainObjectReference objectRefReplicant = reuse.replicate(ref);
        return new WrappedDomainObjectReference(objectRefReplicant, classRef);
    }
}
