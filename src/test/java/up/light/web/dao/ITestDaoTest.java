package up.light.web.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import up.light.web.entity.TestDO;
import up.light.web.entity.UserDO;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
@Rollback
public class ITestDaoTest extends BaseTest {
	@Resource
	private ITestDao dao;

	@DatabaseSetup(value = "test/t_test.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase(value = "test/t_test_insert_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testInsertTest() {
		TestDO test = createTest();
		int c = dao.insertTest(test);
		assertEquals(1, c);
		assertTrue(test.getId() > 0);
	}

	@DatabaseSetup(value = "test/t_test.xml")
	@ExpectedDatabase(value = "test/t_test_delete_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testDeleteTest() {
		int c = dao.deleteTest(3);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "test/t_test_with_result.xml")
	@ExpectedDatabase(value = "test/t_test_delete_proj_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testDeleteTestByProject() {
		int c = dao.deleteTestByProject(2);
		assertEquals(3, c);
	}

	@DatabaseSetup(value = "test/t_test.xml")
	@Test
	public void testSelectTestByPage() {
		List<TestDO> ts = dao.selectTestByPage(1);
		assertEquals(3, ts.size());

		TestDO t = ts.get(2);
		assertEquals(3, t.getId());
		assertEquals(1, t.getPid());
		assertEquals(false, t.isFinish());
		assertEquals("t3", t.getName());
		assertEquals(getTime(8, 1), t.getCreateTime());
		assertEquals(1, t.getDsid());
	}

	private TestDO createTest() {
		TestDO t = new TestDO();
		t.setId(1);
		t.setPid(1);
		t.setFinish(true);
		UserDO u = new UserDO();
		u.setId(2);
		t.setCreator(u);
		t.setName("t1");
		t.setDsid(1);
		return t;
	}

}
