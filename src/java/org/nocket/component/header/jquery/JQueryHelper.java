package org.nocket.component.header.jquery;

import org.apache.wicket.markup.head.IHeaderResponse;

public class JQueryHelper {

    /**
     * Includes jquery in the html header according to the server status
     * (prod/dev)
     * 
     * @param response
     */
    public static void initJQuery(IHeaderResponse response) {
        initJQuery(response, JQueryVersion.JQUERY182);
    }

    /**
     * Includes the provided jquery-version in the html header according to the
     * server status (prod/dev)
     * 
     * @param response
     * @param version
     */
    public static void initJQuery(IHeaderResponse response, JQueryVersion version) {
        // do nothing as of W6 there is native JQuery support from Wicket 
    }

    public static String getCurrentVersion() {
        return JQueryVersion.JQUERY182.filename();
    }

    public enum JQueryVersion {
        JQUERY182("jquery-1.8.2.js");

        private final String filename;

        JQueryVersion(String filename) {
            this.filename = filename;
        }

        private String filename() {
            return filename;
        }
    }
}
