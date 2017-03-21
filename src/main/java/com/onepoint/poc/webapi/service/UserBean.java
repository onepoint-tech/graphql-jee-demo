package com.onepoint.poc.webapi.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.onepoint.poc.webapi.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class UserBean {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private ProjectBean projectBean;

	public UserBean() {
		super();
	}

	public User add(final User user) {
		log.info("add");

		user.setId(UUID.randomUUID().toString());
		em.persist(user);
		return user;
	}

	public Collection<User> findAll() {
		log.info("findAll");

		final TypedQuery<User> findAllQuery = em.createNamedQuery("user.findAll", User.class);
		return findAllQuery.getResultList();
	}

	public Optional<User> find(final String id) {
		log.info("find: {}", id);

		final User user = em.find(User.class, id);
		return Optional.ofNullable(user);
	}

	public Optional<User> assign(final String idUser, final String idProject) {
		log.info("assign {}=>{}", idUser, idProject);
		return find(idUser)
				.map(assignProject(idProject));
	}

	public Function<User, User> assignProject(final String idProject) {
		log.info("assign project {}", idProject);
		return user -> {
			projectBean.find(idProject)
					.ifPresent(user::addProject);
			return user;
		};
	}

}
