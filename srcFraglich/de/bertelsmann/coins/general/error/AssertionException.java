package de.bertelsmann.coins.general.error;

/**
 * <p>
 * Header: DMD3000 Framework Buisness Components
 * </p>
 * <p>
 * Description: Severe exception thrown by the {@link Assert} class in case of
 * an assertion violation. Deriving this assertion from
 * {@link LoggedSevereException} makes it a runtime exception which therefore
 * doesn't have to be declared in the methods' signatures.
 * 
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Organisation: arvato systems
 * </p>
 * 
 * @author Jan Lessner / Christoph Apke
 * @version 1.0
 */
public class AssertionException extends RuntimeException {

    public AssertionException(String message) {
	super(message);
    }
}
