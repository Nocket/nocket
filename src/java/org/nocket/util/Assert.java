package org.nocket.util;

public class Assert {

    public static boolean on = true;

    public static AssertionException signal(String message)
	    throws AssertionException {

	/*
	 * Ok, ok, this is somewhat strange but I can explain everything: The
	 * function is supposed to report an AssertionException into the system
	 * error log, so we create the exception *as input* for Err.handler().
	 * On the other hand we also want an AssertionException to be thrown
	 * rather than a LoggedSevereException, so we must call process rather
	 * than processSevere. But the outcome of process is also needed as
	 * input for the AssertionException, so we must instanciate another one
	 * and throw it. We could have added something like setLogRef() in
	 * LoggedSevereException but I'd rather like to see conceptionally
	 * strange things well-encapsulated like in this function ;-)
	 */
	//	String logRef = Err.handler().process(new AssertionException(message));
	throw new AssertionException(/*logRef,*/ message);

    }

    /**
     * Ordinary assertion, throwing an {@link AssertionException} if the passed
     * condition is not true. The passed message is put in the exception.
     */
    public static void test(boolean condition, String message)
	    throws AssertionException {

	if (on && !condition) {
	    signal(message);
	}
    }

    /** Like function above, but without explaination */
    public static void test(boolean condition) throws AssertionException {

	test(condition, null);
    }

    /**
     * Throws an {@link AssertionException} if the passed object is null. The
     * passed message is put in the exception. Returns the passed object allowing
     * to use the assertion directly in an assignment like this:
     * String myString = Assert.notNull(myOtherString)
     */
    public static <T>T notNull(T obj, String message) {
	test(obj != null, message);
	return obj;
    }

    /** Like function above, but with a default explaination */
    public static <T>T notNull(T obj) {
	return notNull(obj, "unexpected null value");
    }

    /**
     * Throws an {@link AssertionException} if the passed object is not null.
     * The passed message is put in the exception.
     */
    public static void isNull(Object obj, String message) {
	test(obj == null, message);
    }

    /** Like function above, but with a default explaination */
    public static void isNull(Object obj) {
	isNull(obj, "unexpected not null value");
    }

    /**
     * Throws an {@link AssertionException} if the passed string is null or
     * empty. The passed message is put in the exception.
     */
    public static String notEmpty(String obj, String message) {
	test(obj != null && obj.length() > 0, message);
	return obj;
    }

    /** Like function above, but with a default explaination */
    public static String notEmpty(String obj) {
	return notEmpty(obj, "unexpected empty value");
    }

    /**
     * Throws an {@link AssertionException} if the passed string is not null or
     * is not empty. The passed message is put in the exception.
     */
    public static void isEmpty(String obj, String message) {
	test(obj == null || obj.length() == 0, message);
    }

    /** Like function above, but with a default explaination */
    public static void isEmpty(String obj) {
	isEmpty(obj, "unexpected not empty value");
    }

    /**
     * Throws an {@link AssertionException} if the passed int is not in the
     * array of allowed values. The passed message is put in the exception.
     */
    public static int in(int value, int[] allowedValues, String message) {
	if (on) {
	    for (int i = 0; i < allowedValues.length; i++) {
		if (value == allowedValues[i]) {
		    break;
		}
	    }
	    signal((message != null) ? message : "illegal value " + value);
	}
	return value;
    }

    /** Like function above, but with a default explaination */
    public static int in(int value, int[] allowedValues) {
	return in(value, allowedValues, null);
    }

    /**
     * Throws an {@link AssertionException} if the passed String is not in the
     * array of allowed Strings. The passed message is put in the exception.
     */
    public static String in(String value, String[] allowedValues, String message) {
	if (on) {
	    notNull(value, message);
	    for (int i = 0; i < allowedValues.length; i++) {
		if (value.equals(allowedValues[i])) {
		    return value;
		}
	    }
	    signal((message != null) ? message : "illegal value " + value);
	}
	return value;
    }

    /** Like function above, but with a default explaination */
    public static String in(String value, String[] allowedValues) {
	return in(value, allowedValues, null);
    }

}
