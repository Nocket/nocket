package dmdweb.gen.domain.visitor.registry;

import gengui.domain.AbstractDomainReference;
import gengui.domain.DomainObjectReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmdweb.gen.domain.element.DomainElementI;

public class DomainRegistry<E extends AbstractDomainReference> {

    private final Map<String, DomainElementI<E>> wicketId_element = new HashMap<String, DomainElementI<E>>();

    void addElement(DomainElementI<E> e) {
        wicketId_element.put(e.getWicketId(), e);
    }

    public DomainElementI<E> getElement(String wicketId) {
        return wicketId_element.get(wicketId);
    }

    public Collection<DomainElementI<E>> getElements() {
        return wicketId_element.values();
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<? extends T> getElements(Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        for (DomainElementI<E> element : wicketId_element.values()) {
            if (clazz.isAssignableFrom(element.getClass())) {
                result.add((T) element);
            }
        }
        return result;
    }

    /**
     * This method works only for domain registries of DomainObjectReferences.
     * Therefore it is not typed to <E>
     */
    public DomainRegistry<DomainObjectReference> replicate(Object domainObject) {
        DomainRegistryReplication reuse = new DomainRegistryReplication(domainObject);
        DomainRegistry<DomainObjectReference> replicant = new DomainRegistry<DomainObjectReference>();
        // Cast in call of replicate() keeps the compiler from complaining
        // We assume the the caller ensures that he has a Domain*OBJECT*Reference typed registry
        // at hand. This should be pretty sure as the caller must pass a domain object for replication 
        Collection<DomainElementI<DomainObjectReference>> replicatedElements = reuse
                .replicate((Collection) getElements());
        for (DomainElementI<DomainObjectReference> element : replicatedElements) {
            replicant.addElement(element);
        }
        return replicant;
    }

}
