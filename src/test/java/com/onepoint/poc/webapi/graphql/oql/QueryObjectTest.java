/**
 *
 */
package com.onepoint.poc.webapi.graphql.oql;

import static graphql.schema.GraphQLSchema.newSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.inject.spi.CDI;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.onepoint.poc.webapi.model.Project;
import com.onepoint.poc.webapi.model.User;
import com.onepoint.poc.webapi.service.ProjectBean;
import com.onepoint.poc.webapi.service.UserBean;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.annotations.GraphQLAnnotations;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;

/**
 * @author s.leduby
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class QueryObjectTest {

	private GraphQL graphql;
	private QueryObject root;
	@Mock
	private UserBean userBean;
	@Mock
	private ProjectBean projectBean;

	@BeforeClass
	@SuppressWarnings("unchecked")
	public static void once() throws Exception {
		// enregistrement du CDI provider mock
		final CDI<Object> cdi = mock(CDI.class);
		CDI.setCDIProvider(() -> cdi);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		final GraphQLObjectType queryRoot = GraphQLAnnotations.object(QueryObject.class);
		final GraphQLSchema schema = newSchema()
				.query(queryRoot)
				.build();
		graphql = new GraphQL(schema);
		root = new QueryObject();

		// Mock la récupération des Bean via CDI
		when(CDI.current().select(UserBean.class)).thenReturn(new UserBeanInstance(userBean));
		when(CDI.current().select(ProjectBean.class)).thenReturn(new ProjectBeanInstance(projectBean));
	}

	private User genUser(final String id) {
		return User.builder()
				.id(id)
				.build();
	}

	private Project genProject(final String id) {
		return Project.builder()
				.id(id)
				.build();
	}

	@Test
	public void testQueryUser() {
		// - Arrange -
		final String idUser = UUID.randomUUID().toString();
		when(userBean.find(idUser)).thenReturn(Optional.of(genUser(idUser)));

		// - Act -
		final ExecutionResult result = graphql.execute("{ user(id: \"" + idUser + "\"){ id firstname } }", root);

		// - Assert -
		assertNoErrors(result);
		final String actual = result.getData().toString();
		assertThat(actual).isEqualTo("{user={id=" + idUser + ", firstname=null}}");
	}

	@Test
	public void testQueryUserWithProject() {
		// - Arrange -
		final String idUser = UUID.randomUUID().toString();
		// chargement de l'utilisateur par son ID
		when(userBean.find(idUser)).thenReturn(Optional.of(genUser(idUser)));
		// chargement des projets associés
		when(projectBean.findByUser(idUser)).thenReturn(Arrays.asList(genProject("1"), genProject("2")));

		// - Act -
		final ExecutionResult result = graphql
				.execute("{ user(id: \"" + idUser + "\"){ id projects { edges { node { id } } } } } }", root);

		// - Assert -
		assertNoErrors(result);
		final String actual = result.getData().toString();
		assertThat(actual).isEqualTo("{user={id=" + idUser + ", projects={edges=[{node={id=1}}, {node={id=2}}]}}}");
	}

	@Test
	public void testQueryUsers() {
		// - Arrange -
		// chargement des utilisateurs
		when(userBean.findAll()).thenReturn(Arrays.asList(genUser("1"), genUser("2"), genUser("3")));

		// - Act -
		final ExecutionResult result = graphql.execute("{ users { edges { node { id } } } }");

		// - Assert -
		assertNoErrors(result);
		final String actual = result.getData().toString();
		assertThat(actual).isEqualTo("{users={edges=[{node={id=1}}, {node={id=2}}, {node={id=3}}]}}");
	}

	@Test
	public void testCreateUser() {
		// - Arrange -
		final String firstname = RandomStringUtils.randomAlphabetic(10);
		final String lastname = RandomStringUtils.randomAlphabetic(10);
		final String idUser = UUID.randomUUID().toString();

		when(userBean.add(argThat(u -> firstname.equals(u.getFirstname()) && lastname.equals(u.getLastname()))))
				.thenReturn(genUser(idUser));

		// - Act -
		final ExecutionResult result = graphql.execute(
				"{ createUser(user: { firstname: \"" + firstname + "\", lastname: \"" + lastname + "\" }) { id } }",
				root);

		// - Assert -
		assertNoErrors(result);
		final String actual = result.getData().toString();
		assertThat(actual).isEqualTo("{createUser={id=" + idUser + "}}");
	}

	@Test
	public void testAssignUserToProject() {
		// - Arrange -
		final String idUser = UUID.randomUUID().toString();
		final String idProject = UUID.randomUUID().toString();
		// affectation de l'utilisateur au projet
		when(userBean.assign(idUser, idProject)).thenReturn(Optional.of(genUser(idUser)));
		// chargement des projets associés pour la réponse
		when(projectBean.findByUser(idUser)).thenReturn(Arrays.asList(genProject(idProject)));

		// - Act -
		final ExecutionResult result = graphql.execute("{ assign(user: \"" + idUser + "\", project: \"" + idProject
				+ "\") { id projects { edges { node { id } } }} }", root);

		// - Assert -
		assertNoErrors(result);
		final String actual = result.getData().toString();
		assertThat(actual).isEqualTo("{assign={id=" + idUser + ", projects={edges=[{node={id=" + idProject + "}}]}}}");
	}

	private void assertNoErrors(final ExecutionResult result) {
		if (!result.getErrors().isEmpty()) {
			log.error("Errors: {}", result.getErrors());
			fail("Errors");
		}
	}

}
