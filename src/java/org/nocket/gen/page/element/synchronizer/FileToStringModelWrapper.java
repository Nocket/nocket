package org.nocket.gen.page.element.synchronizer;

import java.io.File;

import org.apache.wicket.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * Wicket's list components work with Collections but gengui also allows
 * choicers and multi-valued properties to be based on arrays. This wrapper
 * transparently converts arrays from the domain object's property accessors to
 * collections and vice versa.
 * 
 * @author less02
 */
public class FileToStringModelWrapper implements IModel<String> {

    /** The core. */
    private final IModel<File> core;

    /**
     * Instantiates a new file to string model wrapper.
     *
     * @param core the core
     */
    public FileToStringModelWrapper(IModel<File> core) {
        this.core = core;
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
    public String getObject() {
        File coreValue = core.getObject();
        String plattformSpecificPath = coreValue.getPath();
        String neutralPath = plattformSpecificPath.replace(File.separatorChar, '/');
        return neutralPath;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
     */
    @Override
    public void setObject(String value) {
        core.setObject(new File(value));
    }

}
