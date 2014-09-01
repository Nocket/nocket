package dmdweb.gen.domain;

import gengui.domain.AbstractDomainReference;

import java.io.File;

import dmdweb.gen.domain.ref.WrappedDomainReferenceI;

public class FileAndClassNameStrategy<E extends AbstractDomainReference> {

    protected WrappedDomainReferenceI<E> ref;
    protected DMDWebGenContext<E> context;

    public FileAndClassNameStrategy(DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref) {
        this.context = context;
        this.ref = ref;
    }

    public String getFilenamePartAsPath() {
        return getFilenamePart().replace(".", File.separator);
    }

    public String getFilenamePart() {
        return ref.getRef().getDomainClass().getName();
    }

    public boolean isPanel() {
        if (context.getGeneratePanel() != null)
            return context.getGeneratePanel();

        File javaPanelFile = new File(context.getSrcDir() + File.separator + getFilenamePartAsPath() + "Panel.java");
        return javaPanelFile.exists();

    }

    public String getJavaClassPackageNameAsPath() {
        return getJavaClassPackageName().replace(".", File.separator);
    }

    public String getJavaClassPackageName() {
        return context.getRefFactory().getRootReference().getRef().getDomainClass().getPackage().getName();
    }

    public String getJavaClassNamePart() {
        return context.getRefFactory().getRootReference().getRef().getDomainClass().getSimpleName();
    }

}