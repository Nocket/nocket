package org.nocket.util;

import java.util.Hashtable;

/**
 * This class emulates the good old UNIX command line argument reader functions.
 * 
 * @author <a href="mailto:jlessner@gmx.de">Jan Lessner</a>
 */
public class ArgReader {

    public static final char ARGEND = 0;
    public static final char ARGERROR = '$';
    public static final char COLON = ':';
    public static final String COLON_STRING = COLON + "";
    public static final String DASH_STRING = "-";

    private Hashtable<String, String> supportedArgs;
    private String[] args;
    private String argValue = null;
    private int argNo = 0;

    public ArgReader(String[] args, String argpattern) {
	this.args = args;
	this.supportedArgs = new Hashtable<String, String>();

	for (int i = 0; i < argpattern.length()-1; i++) {
	    if(argpattern.charAt(i) != COLON) {
		supportedArgs.put(String.valueOf(argpattern.charAt(i)),
			(argpattern.charAt(i+1) == COLON) ? COLON_STRING : " ");
	    }
	}
    }

    public char getArg() {
	try {

	    char result = ARGEND;
	    boolean flag = false;

	    if (argNo < args.length) {
		if(args[argNo].startsWith(DASH_STRING)) {
		    if(supportedArgs.get(String.valueOf(args[argNo].charAt(1))) != null) {
			if(supportedArgs.get(String.valueOf(args[argNo].charAt(1))).equals(COLON_STRING)) {
			    if(args[argNo].length() > 2) {
				argValue = args[argNo].substring(2);
				args[argNo] = DASH_STRING + args[argNo].charAt(1);
				flag = false;
			    }
			    else {
				argValue = args[argNo + 1];
				flag = true;
			    }
			}
			else {
			    argValue = null;
			}
			result = args[argNo].charAt(1);

			if(args[argNo].length() > 2) {
			    args[argNo] = DASH_STRING + args[argNo].substring(2);
			}
			else {
			    if(flag == true) {
				argNo++;
			    }
			    argNo++;
			}
		    }
		    else {
			throw new IllegalArgumentException("No valid Argument: " + args[argNo].charAt(1));
		    }
		}
		else {
		    result = ARGEND;
		}
	    }
	    else {
		result = ARGEND;
	    }
	    return result;
	}
	catch (Exception ex) {
	    throw new IllegalArgumentException("Parameter is missing");
	}
    }

    public String getArgValue() {
	return argValue;
    }

    public String[] getPendingArgs() {

	String[] pendingargs = new String[args.length - argNo];

	int l = 0;

	for(int i = argNo; i < args.length; i++) {
	    pendingargs[l++] = args[i];
	}
	return pendingargs;
    }
}

