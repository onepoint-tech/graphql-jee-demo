package com.onepoint.poc.webapi.graphql.oql;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;

import com.onepoint.poc.webapi.model.User;
import com.onepoint.poc.webapi.service.ProjectBean;

import graphql.annotations.GraphQLConnection;
import graphql.annotations.GraphQLField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author s.leduby
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserObject {

	@GraphQLField
	private String id;

	@GraphQLField
	private String firstname;
	@GraphQLField
	private String lastname;

	@GraphQLField
	@GraphQLConnection
	public List<ProjectObject> projects() {
		return getProjectbean().findByUser(id).stream()
				.map(ProjectObject::toProjectObject)
				.collect(Collectors.toList());
	}

	public static UserObject toUserObject(final User user) {
		return builder()
				.id(user.getId())
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.build();
	}

	protected ProjectBean getProjectbean() {
		return CDI.current().select(ProjectBean.class).get();
	}

}
