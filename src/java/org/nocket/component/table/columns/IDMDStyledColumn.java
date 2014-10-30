package org.nocket.component.table.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

// TODO: Auto-generated Javadoc
/**
 * Interface that allows directy styling of table heads.
 *
 * @author blaz02
 * @param <T> the generic type
 * @param <S> the generic type
 */

public interface IDMDStyledColumn<T, S> extends IColumn<T, S> {

    /**
     * Returns column CSS style. This styl will be rendered directl as
     * style="..." attribute of the &lt;th&gt; tag.
     * 
     * @return Style for the &lt;th&gt; tag. Can be null.
     */
    public String getCssStyleAttribute();

}
