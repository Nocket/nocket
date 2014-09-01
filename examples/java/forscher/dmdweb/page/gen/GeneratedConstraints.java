package forscher.dmdweb.page.gen;

import javax.validation.constraints.NotNull;

/**
 * This interface declares methods of the Generated class in order to define the
 * constraints separately. This is of interest, when the Getters and Setters of
 * the class are generated and may not be annotated manually
 * 
 * @author less02
 * 
 */
public interface GeneratedConstraints {

    @NotNull
    String getSeparatedConstraint();

}
