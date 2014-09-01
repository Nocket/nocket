package dmdweb.gen.domain.visitor.registry;

import gengui.domain.DomainObjectDecoration;
import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ConnectionReuse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import dmdweb.gen.domain.element.DomainElementI;

public class DomainRegistryReplication extends ConnectionReuse {

    public DomainRegistryReplication(Object domainObject) {
	super(domainObject, null);
    }

    public List<DomainElementI<DomainObjectReference>> replicate(Collection<DomainElementI<DomainObjectReference>> prototypes) {
	List<DomainElementI<DomainObjectReference>> replicants = new ArrayList<DomainElementI<DomainObjectReference>>();
	DomainElementI<DomainObjectReference> anyElement = null;
	if (prototypes.size() > 0) { // Filter special case of a completely uninteractive mask, usually a splash screen
	    for (DomainElementI<DomainObjectReference> prototype: prototypes) {
		DomainElementI<DomainObjectReference> domainElement = prototype.replicate(this);
		replicants.add(domainElement);
		anyElement = prototype;
	    }
	    Map<String, DomainObjectDecoration> protoInterceptions = anyElement.getAccessor().getRef().getDecorations();
	    populateDecorations(protoInterceptions);
	}
	return replicants;
    }

}
