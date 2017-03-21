/**
 *
 */
package com.onepoint.poc.webapi.graphql.oql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.validation.constraints.NotNull;

import com.onepoint.poc.webapi.service.ProjectBean;
import com.onepoint.poc.webapi.service.UserBean;

import graphql.annotations.GraphQLConnection;
import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLInvokeDetached;
import graphql.annotations.GraphQLName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Point d'entré pour l'accès au modèle de données.
 *
 * @author s.leduby
 */
@Slf4j
@Data
public class QueryObject {

	/**
	 * Permet de rechercher un utilisateur par son Id.
	 *
	 * @param id
	 *            Identifiant d'un utilisateur.
	 * @return L'utilisateur correspondant à l'identifiant donné s'il existe.
	 */
	@GraphQLField
	public UserObject user(@NotNull @GraphQLName("id") final String id) {
		log.info("fetching user #{}", id);
		return getUserBean().find(id)
				.map(UserObject::toUserObject)
				.get();
	}

	/**
	 * Fourni la liste des utilisateurs.
	 *
	 * @return Les utilisateurs
	 */
	@GraphQLField
	@GraphQLConnection
	@GraphQLInvokeDetached
	public List<UserObject> users() {
		log.info("fetching users");
		return getUserBean().findAll().stream()
				.map(UserObject::toUserObject)
				.collect(Collectors.toList());
	}

	/**
	 * Permet de rechercher un projet par son Id.
	 *
	 * @param id
	 *            Identifiant d'un projet.
	 * @return Le projet correspondant à l'identifiant donné s'il existe.
	 */
	@GraphQLField
	public ProjectObject project(@NotNull @GraphQLName("id") final String id) {
		log.info("fetching project #{}", id);
		return getProjectBean().find(id)
				.map(ProjectObject::toProjectObject)
				.get();
	}

	/**
	 * Fourni la liste des projets.
	 *
	 * @return Les projets
	 */
	@GraphQLField
	@GraphQLConnection
	@GraphQLInvokeDetached
	public List<ProjectObject> projects() {
		log.info("fetching projects");
		return getProjectBean().findAll().stream()
				.map(ProjectObject::toProjectObject)
				.collect(Collectors.toList());
	}

	/**
	 * Ajoute un utilisateur.
	 *
	 * @param user
	 * @return
	 */
	@GraphQLField
	@GraphQLInvokeDetached
	public UserObject createUser(@NotNull @GraphQLName("user") final UserInput user) {
		return Optional.of(getUserBean().add(user.toUser()))
				.map(UserObject::toUserObject)
				.get();
	}

	/**
	 * Affecte un utilisateur sur un projet.
	 *
	 * @param userId
	 * @param projectId
	 * @return
	 */
	@GraphQLField
	@GraphQLInvokeDetached
	public UserObject assign(@NotNull @GraphQLName("user") final String userId,
			@NotNull @GraphQLName("project") final String projectId) {
		return getUserBean().assign(userId, projectId)
				.map(UserObject::toUserObject)
				.get();
	}

	protected UserBean getUserBean() {
		return getInstance(UserBean.class).get();
	}

	protected ProjectBean getProjectBean() {
		return getInstance(ProjectBean.class).get();
	}

	private <T> Instance<T> getInstance(final Class<T> type) {
		return CDI.current().select(type);
	}

}
