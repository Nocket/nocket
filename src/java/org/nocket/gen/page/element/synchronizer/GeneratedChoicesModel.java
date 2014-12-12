package org.nocket.gen.page.element.synchronizer;

import gengui.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.nocket.gen.page.element.PageElementI;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneratedChoicesModel.
 */
public class GeneratedChoicesModel extends AbstractReadOnlyModel<List<Object>> {

    /** The helper. */
    private SynchronizerHelper helper;
    
    /** The is multivalued property. */
    private boolean isMultivaluedProperty;

    /**
     * Instantiates a new generated choices model.
     *
     * @param element the element
     */
    public GeneratedChoicesModel(PageElementI<Object> element) {
        this.helper = new SynchronizerHelper(element);
        isMultivaluedProperty = ReflectionUtil.isMultivalued(helper.getGetterMethod());
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
     */
    @Override
    public List<Object> getObject() {
        Object[] choices = helper.getChoices();
        List<Object> choicesList = new ArrayList<Object>();
        //remove null since isNullValid controls that in DomainComponentBehaviour
        for (Object c : choices) {
            if (c != null) {
                choicesList.add(c);
            }
        }
        addInvalidChoices(choicesList);
        return choicesList;
    }

    /**
     * This method adds values to the list of options which are not allowed but
     * which are already chosen, usually by programmatic manipulation of the
     * underlying object rather then the GUI.
     *
     * @param choicesList the choices list
     */
    protected void addInvalidChoices(List<Object> choicesList) {
        Object rawChosen = helper.invokeGetterMethod();
        Object[] allChosen = (isMultivaluedProperty && rawChosen != null) ? unpackCollectionOrArray(rawChosen)
                : new Object[] { rawChosen };
        for (Object chosenItem : allChosen) {
            //if choicer does not have the set value, add it like gengui does in that case
            if (chosenItem != null && !choicesList.contains(chosenItem)) {
                choicesList.add(chosenItem);
            }
        }
    }

    /**
     * Unpack collection or array.
     *
     * @param collectionOrArray the collection or array
     * @return the object[]
     */
    protected Object[] unpackCollectionOrArray(Object collectionOrArray) {
        return (collectionOrArray instanceof Collection) ? ReflectionUtil.boxCollection((Collection) collectionOrArray)
                : ReflectionUtil.boxArray(collectionOrArray);
    }

}
