package org.nocket.gen.domain.ref;

import gengui.domain.DomainClassReference;
import gengui.guiadapter.ConnectionReuse;

public class WrappedDomainClassReference implements WrappedDomainReferenceI<DomainClassReference> {

	private final DomainClassReference ref;

	public WrappedDomainClassReference(DomainClassReference ref) {
		this.ref = ref;
	}

	@Override
	public DomainClassReference getRef() {
		return ref;
	}

	@Override
	public DomainClassReference getClassRef() {
		return ref;
	}

	@Override
	public WrappedDomainClassReference replicate(ConnectionReuse reuse) {
		return new WrappedDomainClassReference(ref);
	}
}
