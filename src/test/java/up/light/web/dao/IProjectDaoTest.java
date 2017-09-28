package up.light.web.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import up.light.web.BaseTest;
import up.light.web.entity.ProjectDO;
import up.light.web.entity.UserDO;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
@Rollback
public class IProjectDaoTest extends BaseTest {
	@Resource
	private IProjectDao dao;

	@DatabaseSetup(value = "project/t_project.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase(value = "project/t_project_insert_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testInsertProject() {
		ProjectDO proj = createProject();
		int c = dao.insertProject(proj);
		assertEquals(1, c);
		assertTrue(proj.getId() > 0);
	}

	@DatabaseSetup(value = "project/t_project.xml")
	@ExpectedDatabase(value = "project/t_project_update_exp.xml", table = "t_project")
	@Test
	public void testUpdateProject() {
		ProjectDO proj = createProject();
		proj.setId(2);
		proj.setName("unit");
		proj.setDescription("unit");
		proj.setCreateTime(new Date());
		UserDO u = new UserDO();
		u.setId(100);
		proj.setCreator(u);
		int c = dao.updateProject(proj);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "project/t_project.xml")
	@ExpectedDatabase(value = "project/t_project_delete_exp.xml", table = "t_project")
	@Test
	public void testDeleteProject() {
		int c = dao.deleteProject(2);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "project/t_project.xml")
	@Test
	public void testSelectAllProjects() {
		List<ProjectDO> ps = dao.selectAllProjects();
		assertEquals(2, ps.size());

		ProjectDO p = ps.get(0);
		assertEquals(1, p.getId());
		assertEquals("p1", p.getName());
		assertEquals("desc1", p.getDescription());
		assertEquals(getTime(8, 1), p.getCreateTime());
		UserDO u = p.getCreator();
		assertCreatorInfo(u);

		p = ps.get(1);
		assertEquals(2, p.getId());
		assertEquals("p2", p.getName());
		assertEquals("desc2", p.getDescription());
		assertEquals(getTime(8, 1), p.getCreateTime());
		u = p.getCreator();
		assertCreatorInfo(u);
	}

	private ProjectDO createProject() {
		ProjectDO proj = new ProjectDO();
		proj.setName("p1");
		proj.setDescription("desc1");
		UserDO u = new UserDO();
		u.setId(2);
		proj.setCreator(u);
		return proj;
	}

	private void assertCreatorInfo(UserDO u) {
		assertEquals(2, u.getId());
		assertEquals("test", u.getUsername());
		assertNull(u.getPassword());
		assertEquals("普通测试用户", u.getNickname());
		assertEquals(false, u.isEnable());
		assertNull(u.getLastTime());
		assertNull(u.getRole());
	}

}
