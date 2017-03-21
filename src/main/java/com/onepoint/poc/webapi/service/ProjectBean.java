package com.onepoint.poc.webapi.service;

import java.util.Collection;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.onepoint.poc.webapi.model.Project;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class ProjectBean {

	@PersistenceContext
	private EntityManager em;

	public ProjectBean() {
		super();
	}

	public Collection<Project> findAll() {
		log.info("findAll");

		final TypedQuery<Project> findAllQuery = em.createNamedQuery("project.findAll", Project.class);
		return findAllQuery.getResultList();
	}

	public Optional<Project> find(final String id) {
		log.info("find #{}", id);

		final Project project = em.find(Project.class, id);
		return Optional.ofNullable(project);
	}

	public Collection<Project> findByUser(final String idUser) {
		log.info("findByUser #{}", idUser);

		final TypedQuery<Project> findAllQuery = em.createNamedQuery("project.findByUser", Project.class);
		findAllQuery.setParameter("idUser", idUser);
		return findAllQuery.getResultList();
	}

}
