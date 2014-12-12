package org.nocket.gen.page.element.synchronizer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.wicket.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * Wicket's list components work with Collections but gengui also allows
 * choicers and multi-valued properties to be based on arrays. This wrapper
 * transparently converts arrays from the domain object's property accessors to
 * collections and vice versa.
 *
 * @author less02
 * @param <E> the element type
 */
public class ArrayToCollectionModelWrapper<E> implements IModel<E> {

    /** The core. */
    private final IModel<E> core;
    
    /** The array element type. */
    private final Class arrayElementType;

    /**
     * Instantiates a new array to collection model wrapper.
     *
     * @param core the core
     * @param arrayElementType the array element type
     */
    public ArrayToCollectionModelWrapper(IModel<E> core, Class arrayElementType) {
        this.core = core;
        this.arrayElementType = arrayElementType;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    @Override
    public void detach() {
        core.detach();
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IModel#getObject()
     */
    @Override
    public E getObject() {
        Object value = core.getObject();
        return (E) convertArrayToCollection(value);
    }

    /**
     * Convert array to collection.
     *
     * @param value the value
     * @return the object
     */
    public static Object convertArrayToCollection(Object value) {
        if (value != null && value.getClass().isArray()) {
            if (!value.getClass().getComponentType().isPrimitive()) {
                return new ArrayList(Arrays.asList((Object[]) value));
            } else {
                int length = Array.getLength(value);
                ArrayList<Object> result = new ArrayList<Object>();
                for (int i = 0; i < length; i++)
                    result.add(Array.get(value, i));
                return result;
            }
        }
        return value;
    }

    /**
     * Convert collection to array.
     *
     * @param value the value
     * @param arrayElementType the array element type
     * @return the object
     */
    public static Object convertCollectionToArray(Object value, Class arrayElementType) {
        if (value instanceof Collection) {
            Collection collectionValue = (Collection) value;
            Object array = Array.newInstance(arrayElementType, collectionValue.size());
            return collectionValue.toArray((Object[]) array);
        }
        return value;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
     */
    @Override
    public void setObject(E value) {
        core.setObject((E) convertCollectionToArray(value, arrayElementType));
    }

}
