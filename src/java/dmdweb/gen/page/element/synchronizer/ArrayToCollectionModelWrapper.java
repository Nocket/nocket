package dmdweb.gen.page.element.synchronizer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.wicket.model.IModel;

/**
 * Wicket's list components work with Collections but gengui also allows
 * choicers and multi-valued properties to be based on arrays. This wrapper
 * transparently converts arrays from the domain object's property accessors to
 * collections and vice versa.
 * 
 * @author less02
 */
public class ArrayToCollectionModelWrapper<E> implements IModel<E> {

    private final IModel<E> core;
    private final Class arrayElementType;

    public ArrayToCollectionModelWrapper(IModel<E> core, Class arrayElementType) {
        this.core = core;
        this.arrayElementType = arrayElementType;
    }

    @Override
    public void detach() {
        core.detach();
    }

    @Override
    public E getObject() {
        Object value = core.getObject();
        return (E) convertArrayToCollection(value);
    }

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

    public static Object convertCollectionToArray(Object value, Class arrayElementType) {
        if (value instanceof Collection) {
            Collection collectionValue = (Collection) value;
            Object array = Array.newInstance(arrayElementType, collectionValue.size());
            return collectionValue.toArray((Object[]) array);
        }
        return value;
    }

    @Override
    public void setObject(E value) {
        core.setObject((E) convertCollectionToArray(value, arrayElementType));
    }

}
