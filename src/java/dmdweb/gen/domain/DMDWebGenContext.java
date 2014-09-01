package dmdweb.gen.domain;

import gengui.domain.AbstractDomainReference;

import java.io.File;

import dmdweb.gen.domain.ref.DomainReferenceFactoryI;

public class DMDWebGenContext<E extends AbstractDomainReference> {

    private final Boolean generatePanel;
    private final File srcDir;
    private final File genDir;
    private final LayoutStrategy layoutStrategy;
    private final DomainReferenceFactoryI<E> refFactory;
    private final WebDomainProperties domainProperties;
    private FileAndClassNameStrategy<E> fileAndClassNameStrategy;

    public DMDWebGenContext(Boolean generatePanel, String srcDir, String genDir, LayoutStrategy layoutStrategy,
            DomainReferenceFactoryI<E> refFactory) {
        if (srcDir != null) {
            this.srcDir = new File(srcDir);
        } else {
            this.srcDir = null;
        }
        if (genDir != null) {
            this.genDir = new File(genDir);
        } else {
            this.genDir = null;
        }
        this.generatePanel = generatePanel;
        this.refFactory = refFactory;
        this.layoutStrategy = layoutStrategy;
        this.domainProperties = new WebDomainProperties(refFactory.getRootReference().getRef().getDomainClass());
    }

    public Boolean getGeneratePanel() {
        return generatePanel;
    }

    public File getSrcDir() {
        return srcDir != null ? srcDir : new File(domainProperties.getSrcFilePath());
    }

    public File getGenDir() {
        return genDir != null ? genDir : new File(domainProperties.getHTMLFilePath());
    }

    public DomainReferenceFactoryI<E> getRefFactory() {
        return refFactory;
    }

    public WebDomainProperties getDomainProperties() {
        return domainProperties;
    }

    public LayoutStrategy getLayoutStrategy() {
        return layoutStrategy != null ? layoutStrategy : domainProperties.getHTMLLayoutStrategie();
    }

    public String[] getHeaderlinks() {
        return domainProperties.getHTMLHeaderLinks();
    }

    public FileAndClassNameStrategy<E> getFileAndClassNameStrategy() {
        if (fileAndClassNameStrategy == null) {
            fileAndClassNameStrategy = new FileAndClassNameStrategy<E>(this, refFactory
                    .getRootReference());
        }
        return fileAndClassNameStrategy;
    }

    public void setFileAndClassNameStrategy(FileAndClassNameStrategy<E> fileAndClassNameStrategy) {
        this.fileAndClassNameStrategy = fileAndClassNameStrategy;
    }
}
