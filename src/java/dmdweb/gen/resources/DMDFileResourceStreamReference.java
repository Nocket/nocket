package dmdweb.gen.resources;

import org.apache.wicket.util.file.File;
import org.apache.wicket.util.resource.FileResourceStream;

/**
 * A reference which can be used to recreate {@link FileResourceStream}
 */
public class DMDFileResourceStreamReference extends AbstractDMDResourceStreamReference {
    private final String fileName;

    DMDFileResourceStreamReference(final FileResourceStream fileResourceStream) {
        fileName = fileResourceStream.getFile().getAbsolutePath();
        saveResourceStream(fileResourceStream);
    }

    public FileResourceStream getReference() {
        FileResourceStream fileResourceStream = new FileResourceStream(new File(fileName));
        restoreResourceStream(fileResourceStream);
        return fileResourceStream;
    }
}
