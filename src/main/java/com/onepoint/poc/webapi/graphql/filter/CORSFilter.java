package com.onepoint.poc.webapi.graphql.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Filtre pour configurer le "Cross-origin resource sharing".
 *
 * @author s.leduby
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {
	@Override
	public void filter(final ContainerRequestContext paramContainerRequestContext,
			final ContainerResponseContext paramContainerResponseContext) throws IOException {
		paramContainerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		paramContainerResponseContext.getHeaders().add("Access-Control-Allow-Headers",
				"origin, content-type, accept, authorization, if-modified-since");
		paramContainerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		paramContainerResponseContext.getHeaders().add("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
		paramContainerResponseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
	}
}
