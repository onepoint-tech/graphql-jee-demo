package com.onepoint.poc.webapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "\"user\"") // user est un mot-clé réservé dans certains SGBD
@NamedQueries(@NamedQuery(name = "user.findAll", query = "SELECT u FROM User u"))
@BatchSize(size = 10)
public class User {

	@Id
	private String id;

	private String firstname;
	private String lastname;

	@ManyToMany
	@JoinTable(name = "assignment", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Project> projects;

	public User() {
		super();
		projects = new ArrayList<>();
	}

	public User(final String id, final String firstname, final String lastname, final List<Project> projects) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.projects = Optional.ofNullable(projects).orElse(new ArrayList<>());
	}

	public void addProject(final Project project) {
		projects.add(project);
	}

}
