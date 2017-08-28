package com.cim.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import com.cim.service.AuthenticationService;
import com.cim.service.AuthenticationServiceImpl;
import com.cim.util.Constants;

@Provider
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter {

	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse(Constants.REQUEST_AUTHENTICATION_FAILED, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), new Headers<Object>());
	
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		
		//Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        AuthenticationService authenticator = new AuthenticationServiceImpl();
        if(!authenticator.authenticateRequest(headers.getFirst("username"), headers.getFirst("password"))) {
        	requestContext.abortWith(ACCESS_FORBIDDEN);
            return;	
        }
	}

}
