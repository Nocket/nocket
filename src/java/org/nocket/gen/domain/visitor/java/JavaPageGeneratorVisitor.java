package org.nocket.gen.domain.visitor.java;

import gengui.domain.AbstractDomainReference;

import java.io.File;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;

/**
 * Generates Java Source gode for Panel or page. No merge functionality. If according class exists, it will 
 * do nothing.
 * 
 * @author blaz02
 *
 * @param <E>
 */
public class JavaPageGeneratorVisitor<E extends AbstractDomainReference> extends AbstractJavaGeneratorVisitor<E> {

	protected static final String SUFFIX = "Page";
	
	public JavaPageGeneratorVisitor(DMDWebGenContext<E> context) {
		super(context);
	}

	@Override
	public void visitSimpleProperty(SimplePropertyElement<E> e) {
	}

	@Override
	public void visitChoicerProperty(ChoicerPropertyElement<E> e) {

	}

	@Override
	public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {

	}

	@Override
	public void visitButton(ButtonElement<E> e) {

	}

	@Override
	public void visitResource(ResourceElement<E> e) {

	}

	@Override
	public void visitFieldsetOpen(HeadlineElement<E> e) {

	}

	@Override
	public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
	}

	@Override
	public void visitFieldsetClose() {

	}

	@Override
	public void visitHiddenProperty(HiddenPropertyElement<E> e) {

	}

	@Override
	public void finish() {
		File outputFile = getOutputJavaFile(getOutputFileName());
		if(!outputFile.exists()) {
			StringBuilder gen = new StringBuilder();
			gen.append("package ").append(getPojoPackageName()).append(";\n");
			gen.append("\n").append(getImports()).append("\n");
			gen.append("public class ").append(getJavaClassName()).append(" extends DMDWebPage {\n\n");
			gen.append(INDENT_1).append("private static final long serialVersionUID = 1L;\n");
			gen.append("\n");
			gen.append(INDENT_1).append("public ").append(getJavaClassName()).append("() {\n");
			gen.append(INDENT_2).append("this(Model.of(new " + getPojoClassName()).append("()));\n");
			gen.append(INDENT_1).append("}\n");
			gen.append("\n");
			gen.append(INDENT_1).append("public ").append(getJavaClassName()).append("(final IModel<").append(getPojoClassName()).append("> model) {\n");
	        gen.append(INDENT_2).append("super(model);\n");
	        gen.append(INDENT_2).append("final GeneratedBinding generatedBinding = new GeneratedBinding(this);\n");
	        gen.append(INDENT_2).append("generatedBinding.bind();\n");
			gen.append(INDENT_1).append("}\n");
	        gen.append("}\n");
	        writeToFile(getOutputFileName(), gen);
		}
	}

	protected StringBuilder getImports() {
		StringBuilder sb = new StringBuilder();
		sb.append("import org.apache.wicket.model.IModel;\n");
		sb.append("import org.apache.wicket.model.Model;\n");
		sb.append("import org.nocket.gen.page.GeneratedBinding;\n");
		sb.append("import org.nocket.page.DMDWebPage;\n");
		return sb;
	}

    protected String getJavaClassName() {
        return getPojoClassName() + SUFFIX;
    }

	protected String getOutputFileName() {
		return getJavaClassName() + JAVAEXT;
	}

}