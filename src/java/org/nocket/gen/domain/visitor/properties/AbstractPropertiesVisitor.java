package org.nocket.gen.domain.visitor.properties;

import gengui.domain.AbstractDomainReference;
import gengui.util.SevereGUIException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.visitor.AbstractDomainElementVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractPropertiesVisitor.
 *
 * @param <E> the element type
 */
public abstract class AbstractPropertiesVisitor<E extends AbstractDomainReference> extends
        AbstractDomainElementVisitor<E> {

    /** The existing properties. */
    private Properties existingProperties;
    
    /** The new properties ordered. */
    protected final List<Properties> newPropertiesOrdered;

    /**
     * Instantiates a new abstract properties visitor.
     *
     * @param context the context
     */
    public AbstractPropertiesVisitor(DMDWebGenContext context) {
        super(context);
        this.newPropertiesOrdered = new ArrayList<Properties>();
    }

    /**
     * Gets the existing properties.
     *
     * @return the existing properties
     */
    protected Properties getExistingProperties() {
        if (existingProperties == null) {
            existingProperties = loadProperties();
        }
        return existingProperties;
    }

    /**
     * Load properties.
     *
     * @return the properties
     */
    protected Properties loadProperties() {
        File propertiesFile = getPropertiesFile();
        Properties properties = new Properties();
        if (propertiesFile.exists()) {
            try {
                properties.load(new FileInputStream(propertiesFile));
            } catch (Exception e) {
                throw new SevereGUIException(e);
            }
        }
        return properties;
    }

    /**
     * Gets the properties file.
     *
     * @return the properties file
     */
    protected abstract File getPropertiesFile();

    /**
     * Adds the property.
     *
     * @param key the key
     * @param value the value
     * @return true, if successful
     */
    protected boolean addProperty(String key, String value) {
        if (overwriteExistingProperties() || getExistingProperties().get(key) == null) {
            Properties newProperty = new Properties();
            newProperty.setProperty(key, value);
            newPropertiesOrdered.add(newProperty);
            return true;
        }
        return false;
    }

    /**
     * If true, existing properties will be always overwritten. False by
     * default.
     *
     * @return true, if successful
     */
    protected boolean overwriteExistingProperties() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#finish()
     */
    @Override
    public void finish() {
        if (newPropertiesOrdered.size() > 0) {
            try {
                // using properties to write into file to get the automatic
                // unicode encoding for the values
                // writing from seperate properties instances to preserve
                // properties order, which get lost on
                // properties.store()
                String appendString = "";
                if (getExistingProperties().size() > 0) {
                    appendString += "\n";
                }
                for (Properties newProperties : newPropertiesOrdered) {
                    ByteArrayOutputStream encodedProperties = new ByteArrayOutputStream();
                    newProperties.store(encodedProperties, "");
                    BufferedReader reader = new BufferedReader(new StringReader(encodedProperties.toString()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("=")) {
                            appendString += line + "\n";
                        }
                    }
                }
                FileOutputStream fosAppender = new FileOutputStream(getPropertiesFile(), true);
                try {
                    IOUtils.write(appendString, fosAppender);
                } finally {
                    IOUtils.closeQuietly(fosAppender);
                }
            } catch (IOException e) {
                throw new SevereGUIException(e);
            }
        }
    }

}