package com.cim.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.server.embedded.SecurityDomain;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;

public class EmbeddedServer implements AutoCloseable {
	private int port;

    private Set<Object> objects = new HashSet<Object>();
    private Set<Class> classes = new HashSet<Class>();

    private TJWSEmbeddedJaxrsServer server;
    private SecurityDomain securityDomain;
    private ResteasyClient resteasyClient;
    private String bindAddress = "localhost";

    private EmbeddedServer(Object... objects) {

        append(objects);
    }

    /**
     * Create instance and pass given instances/classes as singletons/proviers.
     *
     * @param objects
     * @return running instance of {@link EmbeddedServer}
     * @throws IOException
     */
    public static EmbeddedServer create(Object... objects) throws IOException {

        return create(null, objects);
    }

    /**
     * Create instance and pass given instances/classes as singletons/proviers.
     *
     * @param objects
     * @return running instance of {@link EmbeddedServer}
     * @throws IOException
     */
    public static EmbeddedServer create(SecurityDomain securityDomain, Object... objects) throws IOException {

        EmbeddedServer embeddedServer = new EmbeddedServer(objects);
        embeddedServer.withDefaults(securityDomain);

        embeddedServer.start();
        return embeddedServer;
    }

    private void append(Object... objects) {
        for (Object object : objects) {
            if (object instanceof Class) {
                classes.add((Class) object);
            } else {
                this.objects.add(object);
            }
        }
    }

    private void withDefaults(SecurityDomain securityDomain) {
        this.securityDomain = securityDomain;
        this.resteasyClient = new ResteasyClientBuilder().build();
    }

    private void start() throws IOException {

        port = findFreePort();

        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(port);
        server.setBindAddress(bindAddress);
        server.setSecurityDomain(securityDomain);

        for (Object object : objects) {
            if (object instanceof Application) {
                server.getDeployment().setApplication((Application) object);
            } else {
                server.getDeployment().getResources().add(object);
            }
        }

        for (Class resourceOrProvider : classes) {

            if (Application.class.isAssignableFrom(resourceOrProvider)) {
                server.getDeployment().setApplicationClass(resourceOrProvider.getName());
            } else {
                server.getDeployment().getProviderClasses().add(resourceOrProvider.getName());
            }
        }

        server.start();
    }

    /**
     * @return baseURI (http://localhost:PORT) to the REST server.
     */
    public String baseUri() {
        return "http://" + bindAddress + ":" + port;
    }

    /**
     * Begin a new {@link ResteasyWebTarget} with additional, relative path with leading /.
     *
     * @param uriTemplate
     * @return ResteasyWebTarget
     */
    public ResteasyWebTarget newRequest(String uriTemplate) {
        return resteasyClient.target(baseUri() + uriTemplate);
    }

    /**
     * Find a free server port.
     *
     * @return port number.
     * @throws IOException
     */
    public static int findFreePort() throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();
        return port;
    }

    /**
     * Close the server and free resources.
     */
    public void close() throws Exception {
        if (server != null) {
            server.stop();
            server = null;
        }
    }
}
