package org.nocket.gen.page.element.synchronizer;

import gengui.util.SevereGUIException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.nocket.NocketSession;
import org.nocket.gen.page.element.PageElementI;

public class FileUploadModel implements IModel<List<FileUpload>>, Serializable {

    protected SynchronizerHelper helper;

    public FileUploadModel(PageElementI<Object> element) {
        this.helper = new SynchronizerHelper(element);
    }

    @Override
    public void detach() {
    }

    @Override
    public List<FileUpload> getObject() {
        return null;
    }

    @Override
    public void setObject(List<FileUpload> object) {
        if (object != null) {
            FileUpload uploadedFile = object.get(0);
            try {
                File file = NocketSession.get().getFileInSessionDir(uploadedFile.getClientFileName());
                FileUtils.deleteQuietly(file);
                uploadedFile.writeTo(file);
                helper.invokeSetterMethod(file);
            } catch (IOException iox) {
                throw new SevereGUIException(iox);
            }
        }
    }
}
