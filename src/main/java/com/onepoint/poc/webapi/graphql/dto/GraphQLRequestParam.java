/**
 *
 */
package com.onepoint.poc.webapi.graphql.dto;

import java.util.Map;

import lombok.Data;

/**
 * @author s.leduby
 */
@Data
public class GraphQLRequestParam {

	private String operationName;
	private String query;
	private Map<String, Object> variables;
}
