package dmdweb.component.link;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.AbstractResource.ResourceResponse;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource.Attributes;

import dmdweb.gen.page.visitor.bind.builder.LinkResource;
import dmdweb.gen.page.visitor.bind.builder.LinkResource.ResourceResponseFile;

public class DMDResourceLink extends Link implements IResourceListener {

    private static final long serialVersionUID = 1L;

    /** The Resource */
    private final LinkResource resource;

    private String contentType;

    private ContentDisposition contentDisposition = ContentDisposition.ATTACHMENT;

    private String textEncoding;

    private String filename;

    public DMDResourceLink(String id, final IModel<?> model) {
        super(id);
        this.resource = new LinkResource(model) {

            private static final long serialVersionUID = 1L;

            @Override
            protected ResourceResponse createResourceResponse() {
                return DMDResourceLink.this.createResourceResponse();
            }

        };
    }

    public ResourceResponse createResourceResponse() {
        ResourceResponseFile resourceResponseFile = new ResourceResponseFile();
        resourceResponseFile.setFileName(filename);
        resourceResponseFile.setContentType(contentType);
        resourceResponseFile.setTextEncoding(textEncoding);
        resourceResponseFile.setContentDisposition(contentDisposition);
        return resourceResponseFile;
    }

    /**
     * @see org.apache.wicket.markup.html.link.Link#onClick()
     */
    @Override
    public void onClick()
    {
    }

    /**
     * @see org.apache.wicket.IResourceListener#onResourceRequested()
     */
    @Override
    public final void onResourceRequested()
    {

        Attributes a = new Attributes(RequestCycle.get().getRequest(), RequestCycle.get()
                .getResponse(), null);
        resource.respond(a);
        onLinkClicked();
    }

    /**
     * @see org.apache.wicket.markup.html.link.Link#getURL()
     */
    @Override
    protected final CharSequence getURL()
    {
        return urlFor(IResourceListener.INTERFACE, null);
    }

    public void withContentType(String contentType) {
        this.contentType = contentType;
    }

    public void withContentDisposition(ContentDisposition contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public void withTextEncoding(String textEncoding) {
        this.textEncoding = textEncoding;
    }

    public void withFilename(String filename) {
        this.filename = filename;
    }
}
