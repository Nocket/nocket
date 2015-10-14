package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;
import java.util.Map;

import org.apache.ecs.Element;
import org.apache.ecs.html.FieldSet;
import org.apache.ecs.html.Label;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;

/**
 * Styling-Strategie legt fest, wie der HTML Generator die Webseiten generiert und dazu noch wie 
 * die durch Nocket generierten Komponenten für die einzelnen Views aussehen.
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface StylingStrategyI {
	
	public Label createFieldsetHeaderLabel(HeadlineElement<?> headlineElement);
	
	public Label createLabel(DomainElementI<?> de, Element e);
    
	public Element createSimpleProperty(SimplePropertyElement<?> e);

	public Element createChoicerProperty(ChoicerPropertyElement<?> e);  
	    
	public Element createCheckboxProperty(CheckboxPropertyElement<?> e);

	public FieldSet createFieldset(HeadlineElement<?> headlineElement);
	
	public Element createButton(ButtonElement<?> e);

    public Element createResource(ResourceElement<?> e);

	public Element createMultivalueProperty(MultivaluePropertyElement<?> e);
	
	/**
	 * Methode liefert den WebMenuBuilder für die aktuell genutzte Styling-Strategie
	 */
	public WebMenuBuilderI getWebMenuBuilder();

	/**
	 * Methode liefert den FeedbackPanelBuilder für die aktuell genutzte Styling-Strategie
	 */
	public FeedbackPanelBuilderI getFeedbackPanelBuilder();
	
	/**
	 * Methode liefert den ListMultipleChoiceBuilder für die aktuell genutzte Styling-Strategie
	 */
	public ListMultipleChoiceBuilderI<String> getListMultipleChoiceBuilder();
	
	/**
	 * Methode liefert den RadioChoiceBuilder für die aktuell genutzte Styling-Strategie
	 */
	public RadioChoiceBuilderI<?> getRadioChoiceBuilder();
	
	/**
	 * Methode liefert den TabbedPanelBuilder für die aktuell genutzte Styling-Strategie
	 */
	public TabbedPanelBuilderI<ITab> getTabbedPanelBuilder();
	
	/**
	 * Methode liefert den TablePanelBuilder für die aktuell genutzte Styling-Strategie
	 */
	public TablePanelBuilderI<?> getTablePanelBuilder();
	
	/**
	 * Methode liefert den TextFieldBuilder für die aktuell genutzte Styling-Strategie
	 */
	public TextFieldBuilderI getTextFieldBuilder();
	
	/**
	 * Methide kuefert deb FileInputBuilder für die aktuell genutzte Styling-Strategie
	 */
	public FileUploadBuilderI getFileUploadBuilder();
	
	/**
	 * Builder für Passwortfelder erhalten
	 */
	public PasswordTextFieldBuilderI getPasswordTextFieldBuilder();
	
	/**
	 * Builder für Textfelder
	 */
	public TextAreaBuilderI getTextAreaBuilder();
	
	/**
	 * Builder für Checkboxen
	 */
	public CheckBoxBuilderI getCheckBoxBuilder();
	
	/**
	 * Builder für Bilder auf der Seite
	 */
	public ImageBuilderI getImageBuilder();
	
	/**
	 * Builder für interne und externe Links
	 */
	public LinkBuilderI getLinkBuilder();
	
	/**
	 * Builder für DropDown Menüs
	 */
	public DropDownBuilderI getDropDownBuilder();
	
	/**
	 * Builder für Buttons
	 */
	public ButtonBuilderI getButtonBuilder();
	
	/**
	 * Builder für RepeatingViews. (ListViews)
	 */
	public RepeatingViewBuilderI getRepeatingViewBuilder();
	
	/**
	 * Methode liefert eine Liste der einzubindenden CSS-Dateien, die auf jeder Seite der Applikation eingebunden werden sollen
	 */
	public List<CssResourceReference> getCssStyleFiles();
	
	/**
	 * Methode liefert eine Liste der einzubindenden JS-Dateien, die auf jeder Seite der Applikation eingebunden werden sollen
	 */
	public List <JavaScriptResourceReference> getJavascriptFiles();
	
	/**
	 * Global zur Verfügung gestellte Ressourcen für diese Styling-Strategie
	 * - String ist die URL unter der die Ressource erreichbar sein soll
	 * - ResourceReference ist die Ressource die unter der URL erreichbar sein soll
	 */
	public Map<String, ResourceReference> getGlobalResources();
	
	/**
	 * Generatorcontext in die Styling-Strategie setzen
	 */
	public void setContext(DMDWebGenContext<?> context);
	
	/**
	 * Builder für Modale Dialoge
	 */
	public ModalWindowBuilderI getModalWindowBuilder();
}
