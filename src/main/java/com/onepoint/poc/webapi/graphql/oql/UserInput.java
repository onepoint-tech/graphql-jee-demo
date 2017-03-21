/**
 *
 */
package com.onepoint.poc.webapi.graphql.oql;

import java.util.HashMap;
import java.util.Map;

import com.onepoint.poc.webapi.model.User;

import graphql.annotations.GraphQLField;
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
public class UserInput {

	@GraphQLField
	private String firstname;
	@GraphQLField
	private String lastname;

	public UserInput(final HashMap<String, Object> param) {
		super();
		init(param);
	}

	private void init(final Map<String, Object> param) {
		firstname = (String) param.get("firstname");
		lastname = (String) param.get("lastname");
	}

	public User toUser() {
		return User.builder()
				.firstname(getFirstname())
				.lastname(getLastname())
				.build();
	}

}
