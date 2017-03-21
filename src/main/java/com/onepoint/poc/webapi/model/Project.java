package com.onepoint.poc.webapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
@NamedQueries({ @NamedQuery(name = "project.findAll", query = "SELECT p FROM Project p"),
		@NamedQuery(name = "project.findByUser", query = "SELECT p FROM Project p LEFT JOIN p.members m WHERE m.id = :idUser") })
@BatchSize(size = 10)
public class Project {

	@Id
	private String id;

	private String code;
	private String label;

	@ManyToMany
	@JoinTable(name = "assignment", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> members;

}
