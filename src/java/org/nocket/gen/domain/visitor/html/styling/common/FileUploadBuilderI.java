/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;

/**
 * Builder für das Erstellen von Datei-Input-Feldern
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface FileUploadBuilderI {
	
	/**
	 * Initialisierung eines Dateiuploadfeldes 
	 */
	public void initFileUploadBuilder(String wicketId);

	/**
	 * Initialisierung eines Dateiuploadfeldes 
	 */
	public void initFileUploadBuilder(String wicketId, IModel<List<FileUpload>> model);

	/**
	 * Fertiges Upload Feld erhalten
	 */
	public FileUploadField getFileUploadField();
	
}
