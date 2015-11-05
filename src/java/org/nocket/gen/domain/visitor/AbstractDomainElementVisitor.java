package org.nocket.gen.domain.visitor;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.NocketGenerator.GenerationProcessDMDWebGenContext;

import gengui.domain.AbstractDomainReference;

public abstract class AbstractDomainElementVisitor<E extends AbstractDomainReference> implements DomainElementVisitorI<E> {

	private final DMDWebGenContext<E> context;

	public AbstractDomainElementVisitor(DMDWebGenContext<E> context) {
		this.context = context;
	}

	@Override
	public DMDWebGenContext<E> getContext() {
		return context;
	}

	protected void writeStatics(String filename, boolean newFile, boolean changed, String error) {
		if (context instanceof GenerationProcessDMDWebGenContext) {
			GenerationProcessDMDWebGenContext<E> genContext = (GenerationProcessDMDWebGenContext<E>) context;
			genContext.getGenerationContext().addFileData(filename, newFile, changed, error);
		}
	}
}
