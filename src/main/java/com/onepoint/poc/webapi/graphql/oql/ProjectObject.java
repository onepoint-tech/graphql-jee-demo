package com.onepoint.poc.webapi.graphql.oql;

import javax.persistence.Id;

import com.onepoint.poc.webapi.model.Project;

import graphql.annotations.GraphQLField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectObject {

	@Id
	@GraphQLField
	private String id;

	@GraphQLField
	private String code;
	@GraphQLField
	private String label;

	public static ProjectObject toProjectObject(final Project project) {
		return builder()
				.id(project.getId())
				.code(project.getCode())
				.label(project.getLabel())
				.build();
	}
}
