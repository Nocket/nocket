package dmdweb.gen.page.visitor.bind.builder.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * http://www.widas.de/fuer-kunden/softwareentwicklung/softwareblog/45-blog-
 * softwareentwicklung
 * -wicket-security-schutz-vor-sql-injection-durch-validatoren
 * 
 * http://www.mvvm.ro/2011/03/sanitize-strings-against-sql-injection.html
 */
public class SQLInjectionValidator extends StringValidator {

    private static final long serialVersionUID = 1L;

    private final List<Pattern> patterns = new ArrayList<Pattern>();

    public SQLInjectionValidator() {
        patterns.add(Pattern.compile(".*-{2,}.*"));
        patterns.add(Pattern.compile(".*([*]/|/[*]).*"));
        patterns
                .add(Pattern
                        .compile(
                                ".*;(\\s)*(exec|execute|select|insert|update|delete|create|alter|drop|rename|truncate|backup|restore)\\s.*",
                                Pattern.CASE_INSENSITIVE));
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        // Check value against pattern
        Object value = validatable.getValue();
        if (value != null) {
            for (Pattern pattern : patterns) {
                if (pattern.matcher(value.toString()).matches()) {
                    ValidationError error = new ValidationError(this);
                    validatable.error(decorate(error, validatable));
                }
            }
        }
    }

}