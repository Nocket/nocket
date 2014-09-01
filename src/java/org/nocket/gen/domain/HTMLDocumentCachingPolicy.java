package org.nocket.gen.domain;

public enum HTMLDocumentCachingPolicy {
    none, // Don't cache HTML documents, good idea for development phase
    permanent, // Read once, cache forever, best choice for production
    wicket, // Depending on Wicket's configuration type: DEVELOPMENT -> none, DEPLOYMENT -> permanent
    age; // Reload when there is a chance to find out if the HTML has been updated since last read. Nice but unsure
}
