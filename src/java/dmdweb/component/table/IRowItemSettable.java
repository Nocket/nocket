package dmdweb.component.table;

/**
 * Tables implementing this interface are able to use an own 'renderer' for a
 * table row.
 * 
 * @author meis026
 * 
 */
public interface IRowItemSettable {
    public void setRowItemClass(Class rowItemClass);
}
