package dmdweb.util;

public class RichtigSchwereOMGException extends RuntimeException {

    // TODO meis026 Nur ein Wrapper um eine RuntimeException -> War vorher ein Err.handler.processSevere. So komisch gemacht um es kompilierbar zu machen, aber die Stellen spaeter wieder finden zu koennen.
    public RichtigSchwereOMGException() {
    }

    public RichtigSchwereOMGException(String message) {
	super(message);
    }

    public RichtigSchwereOMGException(Throwable cause) {
	super(cause);
    }

    public RichtigSchwereOMGException(String message, Throwable cause) {
	super(message, cause);
    }

    public RichtigSchwereOMGException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause);
    }

}
