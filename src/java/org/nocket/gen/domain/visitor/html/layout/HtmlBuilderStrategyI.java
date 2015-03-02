package org.nocket.gen.domain.visitor.html.layout;

import org.apache.ecs.Element;
import org.apache.ecs.html.FieldSet;
import org.apache.ecs.html.Label;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;

public interface HtmlBuilderStrategyI {

	public Label createFieldsetHeaderLabel(HeadlineElement<?> headlineElement);
	
	public Label createLabel(DomainElementI<?> de, Element e);
    
	public Element createSimpleProperty(SimplePropertyElement<?> e);

	public Element createChoicerProperty(ChoicerPropertyElement<?> e);  
	    
	public Element createCheckboxProperty(CheckboxPropertyElement<?> e);

	public FieldSet createFieldset(HeadlineElement<?> headlineElement);
	
	public Element createButton(ButtonElement<?> e);

    public Element createResource(ResourceElement<?> e);

	public Element createMultivalueProperty(MultivaluePropertyElement<?> e);

}
