package com.onepoint.poc.webapi.graphql;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.onepoint.poc.webapi.graphql.dto.GraphQLRequestParam;
import com.onepoint.poc.webapi.graphql.oql.QueryObject;

import graphql.ExecutionResult;
import lombok.extern.slf4j.Slf4j;

@Path("api")
@Slf4j
public class GraphQLEndpoint {

	@Inject
	private GraphQLProvider gqlProvider;

	/**
	 * Reçoit un document de requête GraphQL :
	 *
	 * <pre>
	 * {
	 *     "operationName": "...",
	 *	   "query": "...",
	 *	   "variables": {}
	 * }
	 * </pre>
	 *
	 * @param param
	 *            Paramètres de la requête GraphQL
	 * @return les données correspondant à la requête reçu
	 */
	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> execute(final GraphQLRequestParam param) {

		final String query = param.getQuery();
		final Map<String, Object> variables = Optional.ofNullable(param.getVariables()).orElse(new HashMap<>());

		final QueryObject root = new QueryObject();
		final ExecutionResult executionResult = gqlProvider.getGraphql().execute(query, root, variables);

		final Map<String, Object> result = new LinkedHashMap<>();
		if (!executionResult.getErrors().isEmpty()) {
			result.put("errors", executionResult.getErrors());
			log.error("Errors: {}", executionResult.getErrors());
		}
		result.put("data", executionResult.getData());
		return result;
	}

}
