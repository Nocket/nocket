package dmdweb.gen.page.visitor.bind.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.time.Duration;

@SuppressWarnings("serial")
public class LinkResource extends AbstractResource {

    private static final long serialVersionUID = 1L;

    public static class ResourceResponseXMLUTF8 extends ResourceResponse implements Serializable {

        public ResourceResponseXMLUTF8() {
            super();
            setContentType("text/xml");
            setTextEncoding("utf-8");
        }
    }

    public static class ResourceResponseHTMLUTF8 extends ResourceResponse implements Serializable {

        public ResourceResponseHTMLUTF8() {
            super();
            setContentType("text/html");
            setTextEncoding("utf-8");
        }
    }

    public static class ResourceResponseFile extends ResourceResponse implements Serializable {

        public ResourceResponseFile() {
            super();
            setContentDisposition(ContentDisposition.ATTACHMENT);
            setCacheDuration(Duration.NONE);
        }
    }

    private IModel<?> model;

    public LinkResource(IModel<?> model) {
        this.model = model;
    }

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
        ResourceResponse resourceResponse = createResourceResponse();
        // if there is no file name defined
        if (StringUtils.isBlank(resourceResponse.getFileName())) {
            if (model.getObject() instanceof File) {
                // if there is a file in the model then use the one from the file
                resourceResponse.setFileName(((File) model.getObject()).getName());
            } else {
                // if it a stream use a default name
                resourceResponse.setFileName("download");
            }
        }

        resourceResponse.setWriteCallback(new WriteCallback() {
            @Override
            public void writeData(Attributes attributes) throws IOException {
                OutputStream outputstream = attributes.getResponse().getOutputStream();
                Object object = model.getObject();
                InputStream inputstream;
                if (object instanceof File) {
                    inputstream = new FileInputStream((File) object);
                } else {
                    inputstream = (InputStream) object;
                }
                if (inputstream != null) {
                    try {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputstream.read(buffer)) != -1)
                        {
                            outputstream.write(buffer, 0, bytesRead);
                        }
                    } finally {
                        inputstream.close();
                    }
                }
            }
        });

        return resourceResponse;

    }

    protected ResourceResponse createResourceResponse() {
        return new ResourceResponseFile();
    }
}
