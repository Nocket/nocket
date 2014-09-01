package dmdweb.run;

import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.http.ssl.SslContextFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartEmbeddedJetty extends AbstractStartEmbeddedJetty {

    @Override
    protected void initServer() {
        timeout = (int) Duration.ONE_HOUR.getMilliseconds();

        server = new Server();
        connector = new SocketConnector();

        // Set some timeout options to make debugging easier.
        connector.setMaxIdleTime(timeout);
        connector.setSoLingerTime(-1);
        connector.setPort(Integer.valueOf(System.getProperty("jetty.port", "8080")));
        server.addConnector(connector);
    }

    @Override
    protected void initSSLConnector() {
        // check if a keystore for a SSL certificate is available, and
        // if so, start a SSL connector on port 8443. By default, the
        // quickstart comes with a Apache Wicket Quickstart Certificate
        // that expires about half way september 2021. Do not use this
        // certificate anywhere important as the passwords are available
        // in the source.

        Resource keystore = Resource.newClassPathResource("/keystore");
        if (keystore != null && keystore.exists()) {
            connector.setConfidentialPort(8443);

            SslContextFactory factory = new SslContextFactory();
            factory.setKeyStoreResource(keystore);
            factory.setKeyStorePassword("wicket");
            factory.setTrustStore(keystore);
            factory.setKeyManagerPassword("wicket");
            SslSocketConnector sslConnector = new SslSocketConnector(factory);
            sslConnector.setMaxIdleTime(timeout);
            sslConnector.setPort(8443);
            sslConnector.setAcceptors(4);
            server.addConnector(sslConnector);

            System.out.println("SSL access to the quickstart has been enabled on port 8443");
            System.out.println("You can access the application using SSL on https://localhost:8443");
            System.out.println();
        }
    }

    @Override
    protected void configureContext() {
        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/");
        bb.setWar("resources/webapp");
        server.setHandler(bb);
    }

    @Override
    protected void startJMXServer() {
        // MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
        // server.getContainer().addEventListener(mBeanContainer);
        // mBeanContainer.start();
    }

    @Override
    protected void runServer() throws Exception {
        System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
        System.out.println(">>> If you don't see any other following console output, you probably");
        System.out.println(">>> have to run 'ant all' to make a web application available.");
        System.out.println(">>> Especially you should very soon see something like ");
        System.out.println(">>> *** Wicket is running in DEVELOPMENT mode ***");
        server.start();
        System.in.read();
        System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
        server.stop();
        server.join();
    }
}
