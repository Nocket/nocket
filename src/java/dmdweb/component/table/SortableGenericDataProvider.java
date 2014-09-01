package dmdweb.component.table;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

@SuppressWarnings("serial")
public class SortableGenericDataProvider<T extends Serializable, S> extends SortableDataProvider<T, String> {

    private IModel<List<T>> alle;

    public SortableGenericDataProvider(IModel<List<T>> data, SortParam<String> sortParam) {
        alle = data;
        setSort(sortParam);
    }

    public Iterator<? extends T> iterator(long first, long count) {
        final List<T> listToSort = alle.getObject();
        if (getSort() != null) {
            Collections.sort(listToSort, new Comparator<T>() {
                public int compare(T o1, T o2) {
                    int dir = getSort().isAscending() ? 1 : -1;
                    String sortProperty = getSort().getProperty();
                    Object prop1 = null;
                    Object prop2 = null;
                    Class propType = null;
                    try {
                        prop1 = PropertyUtils.getProperty(o1, sortProperty);
                        prop2 = PropertyUtils.getProperty(o2, sortProperty);
                        propType = PropertyUtils.getPropertyType(o1, sortProperty);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }

                    return dir * compareByProperty(propType, prop1, prop2);
                }

                private int compareByProperty(Class propType, Object prop1, Object prop2) {
                    int compareResult = 0;

                    if (prop1 != null && prop2 != null && Comparable.class.isAssignableFrom(prop1.getClass())) {
                        compareResult = ((Comparable<Object>) prop1).compareTo(prop2);
                    } else if (prop1 != null && prop2 != null) {
                        compareResult = ConvertUtils.convert(prop1).compareTo(ConvertUtils.convert(prop2));
                    } else if (prop1 == null && prop2 != null) {
                        compareResult = 1;
                    } else if (prop1 != null && prop2 == null) {
                        compareResult = -1;
                    }
                    return compareResult;
                }

            });
        }
        List<T> teilListe = listToSort.subList((int) first, (int) (first + count));
        return teilListe.iterator();
    }

    public long size() {
        return alle.getObject().size();
    }

    public IModel<T> model(final T object) {
        return Model.of(object);
    }

    @Override
    public void detach() {
        super.detach();
    }

}
