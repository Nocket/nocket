package dmdweb.gen.page.visitor;

import dmdweb.gen.page.DMDWebGenPageContext;

public abstract class AbstractPageElementVisitor implements PageElementVisitorI {

	private final DMDWebGenPageContext context;
	
	public AbstractPageElementVisitor(DMDWebGenPageContext context){
		this.context = context;
	}
	
	@Override
	public DMDWebGenPageContext getContext() {
		return context;
	}
	
}
