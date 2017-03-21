/**
 *
 */
package com.onepoint.poc.webapi.graphql;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.onepoint.poc.webapi.graphql.oql.QueryObject;

import graphql.GraphQL;
import graphql.annotations.GraphQLAnnotations;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author s.leduby
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class GraphQLProvider {

	private GraphQL graphql;

	@PostConstruct
	public void init() {
		GraphQLObjectType queryRoot;
		try {
			queryRoot = GraphQLAnnotations.object(QueryObject.class);
		} catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		final GraphQLSchema schema = GraphQLSchema.newSchema()
				.query(queryRoot)
				.build();
		setGraphql(new GraphQL(schema));
	}
}
