package dmdweb.gen.domain.visitor.html;

import gengui.domain.AbstractDomainReference;
import gengui.util.DomainProperties.JfdRetentionStrategy;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.LayoutStrategy;
import dmdweb.gen.domain.element.AbstractDomainElement;
import dmdweb.gen.domain.element.ButtonElement;
import dmdweb.gen.domain.element.CheckboxPropertyElement;
import dmdweb.gen.domain.element.ChoicerPropertyElement;
import dmdweb.gen.domain.element.HeadlineElement;
import dmdweb.gen.domain.element.HiddenPropertyElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.domain.element.ResourceElement;
import dmdweb.gen.domain.element.SimplePropertyElement;
import dmdweb.gen.domain.visitor.DomainElementVisitorI;
import dmdweb.gen.domain.visitor.DummyVisitor;
import dmdweb.gen.domain.visitor.html.gen.HtmlGeneratorVisitorBootstrap;
import dmdweb.gen.domain.visitor.html.gen.HtmlGeneratorVisitorTablegrid;
import dmdweb.gen.domain.visitor.html.merge.HtmlMergeVisitorBootstrap;

public class DelegateHtmlVisitor<E extends AbstractDomainReference> extends AbstractHtmlVisitor<E> {

    private DomainElementVisitorI<E> delegate;

    public DelegateHtmlVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

    protected void lazyInitDelegate(DMDWebGenContext<E> context) {
        boolean fileExists = getHtmlFile().exists();
        JfdRetentionStrategy jfdRetentionStrategy = getContext().getDomainProperties().getJFDRetentionStrategy();
        switch (jfdRetentionStrategy) {
        case merge:
        case silentmerge:
            if (fileExists) {
                //TODO JL: Instanciate Tablegrid-based Merger when it is finished
                this.delegate = new HtmlMergeVisitorBootstrap<E>(context);
            } else {
                this.delegate = generatorForLayoutStrategy(context);
            }
            break;
        case keep:
            if (fileExists) {
                this.delegate = new DummyVisitor<E>(context);
            } else {
                this.delegate = generatorForLayoutStrategy(context);
            }
            break;
        case overwrite:
            this.delegate = generatorForLayoutStrategy(context);
            break;
        case none:
        default:
            throw new UnsupportedOperationException("Unsupported " + JfdRetentionStrategy.class.getSimpleName() + ": "
                    + jfdRetentionStrategy);
        }
    }

    protected DomainElementVisitorI<E> generatorForLayoutStrategy(DMDWebGenContext<E> context) {
        return (context.getLayoutStrategy() == LayoutStrategy.BOOTSTRAP) ? new HtmlGeneratorVisitorBootstrap<E>(context)
                : new HtmlGeneratorVisitorTablegrid<E>(context);
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        doDelegation(e);
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        doDelegation(e);
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        doDelegation(e);
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        doDelegation(e);
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        doDelegation(e);
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        doDelegation(e);
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        doDelegation(e);
    }

    @Override
    public void visitFieldsetClose() {
        delegate.visitFieldsetClose();
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        doDelegation(e);
    }

    protected void doDelegation(AbstractDomainElement<E> e) {
        if (delegate == null) {
            lazyInitDelegate(getContext());
        }
        e.accept(delegate);
    }

    @Override
    public void finish() {
        if (delegate == null) {
            lazyInitDelegate(getContext());
        }
        delegate.finish();
    }

}
