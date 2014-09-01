package dmdweb.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.guiadapter.ConnectionReuse;
import gengui.guiadapter.table.TableModelFactory;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import dmdweb.gen.domain.ref.WrappedDomainReferenceI;
import dmdweb.gen.domain.visitor.DomainElementVisitorI;

public class MultivaluePropertyElement<E extends AbstractDomainReference> extends AbstractDomainElement<E> {

    private List<MultivalueButtonElement<E>> buttonElements;
    private WrappedDomainReferenceI<E> columnAccessor;
    private List<MultivalueColumnElement<E>> columns;

    public MultivaluePropertyElement(WrappedDomainReferenceI<E> accessor, Method method,
            WrappedDomainReferenceI<E> columnAccessor, List<MultivalueButtonElement<E>> buttonElements) {
        super(accessor, method);
        this.columnAccessor = columnAccessor;
        this.buttonElements = buttonElements;
    }

    public List<MultivalueColumnElement<E>> getColumns() {
        if (columns == null) {
            final String[] columnNames;
            final String[] columnPrompts;
            columns = new ArrayList<MultivalueColumnElement<E>>();
            if (ReflectionUtil.hasChoicer(getAccessor().getRef(), getMethod())) {
                columnNames = new String[] { StringUtils.uncapitalize(getPropertyName()) };
                columnPrompts = new String[] { getPrompt() };
                columns.add(new MultivalueColumnElement<E>(getAccessor(), columnNames[0], columnPrompts[0], getMethod()));
            } else {
                Class<?> listType = ReflectionUtil.getCollectionContentType(getMethod());
                columnNames = TableModelFactory.extractListProperties(listType);
                if (columnNamesFound(columnNames)) {
                    for (int i = 0; i < columnNames.length; i++) {
                        columnNames[i] = StringUtils.uncapitalize(columnNames[i]);
                    }
                    columnPrompts = TableModelFactory.columnNames(listType, columnNames);
                    for (int i = 0; i < columnNames.length; i++) {
                        columns.add(new MultivalueColumnElement<E>(columnAccessor, columnNames[i], columnPrompts[i]));
                    }
                }
            }
        }
        return columns;
    }

    private boolean columnNamesFound(final String[] columnNames) {
        // check that columnNames is not empty and not an array with only a null object
        return ArrayUtils.isNotEmpty(columnNames) && !(columnNames.length == 1 && columnNames[0] == null);
    }

    public List<String> getPropertyColumnNames() {
        List<String> propertyColumnNames = new ArrayList<String>();
        for (MultivalueColumnElement<E> c : getColumns()) {
            if (!c.isReadonlyFileType())
                propertyColumnNames.add(c.getColumnName());
        }
        return propertyColumnNames;
    }

    public List<MultivalueColumnElement> getDownloadColumnElements() {
        List<MultivalueColumnElement> downloadElements = new ArrayList<MultivalueColumnElement>();
        for (MultivalueColumnElement<E> e : getColumns()) {
            if (e.isReadonlyFileType())
                downloadElements.add(e);
        }
        return downloadElements;
    }

    public List<MultivalueButtonElement<E>> getButtonElements() {
        return buttonElements;
    }

    @Override
    public void accept(DomainElementVisitorI<E> visitor) {
        visitor.visitMultivalueProperty(this);
    }

    public WrappedDomainReferenceI<E> getColumnAccessor() {
        return columnAccessor;
    }

    @Override
    public boolean repeated() {
        return true;
    }

    @Override
    public DomainElementI<E> replicate(ConnectionReuse reuse) {
        return new MultivaluePropertyElement<E>(this.getAccessor().replicate(reuse), this.getMethod(),
                columnAccessor, buttonElements);
    }

}
