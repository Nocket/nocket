package dmdweb.util;

/**
 * Thrown in case of severe unrecoverable problems at runtime.
 * 
 * @author blaz02
 */
@SuppressWarnings("serial")
public class SevereWebException extends RuntimeException {

	public SevereWebException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SevereWebException(String arg0) {
		super(arg0);
	}

	public SevereWebException(Throwable arg0) {
		super(arg0);
	}

}
