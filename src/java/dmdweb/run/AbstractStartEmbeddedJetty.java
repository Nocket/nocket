package dmdweb.run;

import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;

public abstract class AbstractStartEmbeddedJetty {

    protected Server server;
    protected SocketConnector connector;
    protected int timeout = (int) Duration.ONE_HOUR.getMilliseconds();

    public void start() throws Exception {
        initServer();
        initSSLConnector();
        configureContext();
        try {
            runServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected abstract void initServer();

    protected abstract void initSSLConnector();

    protected abstract void configureContext();

    protected abstract void startJMXServer();

    protected abstract void runServer() throws Exception;
}
