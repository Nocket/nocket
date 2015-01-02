package org.nocket.util;

public class NocketRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NocketRuntimeException() {
    }

    public NocketRuntimeException(String message) {
	super(message);
    }

    public NocketRuntimeException(Throwable cause) {
	super(cause);
    }

    public NocketRuntimeException(String message, Throwable cause) {
	super(message, cause);
    }

    public NocketRuntimeException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause);
    }

}
