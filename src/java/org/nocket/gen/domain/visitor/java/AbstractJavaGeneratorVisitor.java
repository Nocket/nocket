package org.nocket.gen.domain.visitor.java;

import java.io.File;
import java.io.IOException;

import gengui.domain.AbstractDomainReference;
import gengui.util.SevereGUIException;

import org.apache.commons.io.FileUtils;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.visitor.AbstractDomainElementVisitor;

abstract public class AbstractJavaGeneratorVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

	protected static final String INDENT_1 = "\t";
	protected static final String INDENT_2 = "\t\t";
	
	protected static final String JAVAEXT = ".java";
	
	public AbstractJavaGeneratorVisitor(DMDWebGenContext<E> context) {
		super(context);
	}
	
	protected String getPojoClassName() {
        return getContext().getFileAndClassNameStrategy().getJavaClassNamePart();
    }

	protected String getPojoPackageName() {
		return getContext().getFileAndClassNameStrategy().getJavaClassPackageName();
	}
	
	protected File getOutputJavaFile(String fileName) {
		String path = getContext().getGenDir() 
				+ File.separator + getContext().getFileAndClassNameStrategy().getJavaClassPackageNameAsPath() + File.separator + fileName;
		return new File(path);
	}
	
	
	protected void writeToFile(String fileName, StringBuilder sb) {
        try {
        	File output = getOutputJavaFile(fileName);
        	FileUtils.deleteQuietly(output);
            FileUtils.forceMkdir(output.getParentFile());
            FileUtils.writeStringToFile(output, sb.toString());
        } catch (IOException e) {
            throw new SevereGUIException(e);
        }
	}

	abstract protected String getJavaClassName();
	
	abstract protected String getOutputFileName();
	
}
