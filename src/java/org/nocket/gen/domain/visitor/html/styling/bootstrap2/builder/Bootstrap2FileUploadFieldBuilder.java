package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.FileUploadBuilderI;

public class Bootstrap2FileUploadFieldBuilder implements FileUploadBuilderI {

	private FileUploadField field = null;
	
	@Override
	public void initFileUploadBuilder(String wicketId) {
		field = new FileUploadField(wicketId);
	}

	@Override
	public void initFileUploadBuilder(String wicketId,
			IModel<List<FileUpload>> model) {
		field = new FileUploadField(wicketId, model);
	}

	@Override
	public FileUploadField getFileUploadField() {
		return field;
	}

}
