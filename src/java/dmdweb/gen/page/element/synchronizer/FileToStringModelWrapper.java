package dmdweb.gen.page.element.synchronizer;

import java.io.File;

import org.apache.wicket.model.IModel;

/**
 * Wicket's list components work with Collections but gengui also allows
 * choicers and multi-valued properties to be based on arrays. This wrapper
 * transparently converts arrays from the domain object's property accessors to
 * collections and vice versa.
 * 
 * @author less02
 */
public class FileToStringModelWrapper implements IModel<String> {

    private final IModel<File> core;

    public FileToStringModelWrapper(IModel<File> core) {
        this.core = core;
    }

    @Override
    public void detach() {
        core.detach();
    }

    @Override
    public String getObject() {
        File coreValue = core.getObject();
        String plattformSpecificPath = coreValue.getPath();
        String neutralPath = plattformSpecificPath.replace(File.separatorChar, '/');
        return neutralPath;
    }

    @Override
    public void setObject(String value) {
        core.setObject(new File(value));
    }

}
