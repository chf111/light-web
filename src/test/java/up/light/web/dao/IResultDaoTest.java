package up.light.web.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import up.light.web.entity.ResultDO;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
@Rollback
public class IResultDaoTest extends BaseTest {
	@Resource
	private IResultDao dao;

	@DatabaseSetup(value = "result/t_result.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase(value = "result/t_result_insert_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testInsertResult() {
		ResultDO r = createResult();
		int c = dao.insertResult(r);
		assertEquals(1, c);
		assertTrue(r.getId() > 0);
	}

	@DatabaseSetup(value = "result/t_result.xml")
	@ExpectedDatabase(value = "result/t_result_update_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testUpdateResult() {
		ResultDO r = new ResultDO();
		r.setId(2);
		r.setTid(100);
		Date d = getTime(1, 1);
		r.setStartTime(d);
		r.setEndTime(d);
		Map<String, String> m = new HashMap<>();
		m.put("id", "1");
		m.put("msg", "hello");
		r.setDetail(m);
		r.setPic("1.png");

		int c = dao.updateResult(r);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "result/t_result.xml")
	@ExpectedDatabase(value = "result/t_result_delete_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testDeleteResultByTest() {
		int c = dao.deleteResultByTest(1);
		assertEquals(2, c);
	}

	@DatabaseSetup(value = "result/t_result.xml")
	@Test
	public void testSelectResultByPage() {
		List<ResultDO> rs = dao.selectResultByPage(1);
		assertEquals(2, rs.size());
		ResultDO r = rs.get(0);
		assertEquals(1, r.getId());
		assertEquals(1, r.getTid());
		assertEquals(0, r.getStatus());
		Date d = getTime(8, 1);
		assertEquals(d, r.getStartTime());
		assertEquals(d, r.getEndTime());
		Map<String, String> detail = r.getDetail();
		assertEquals(2, detail.size());
		assertEquals("1", detail.get("id"));
		assertEquals("hello", detail.get("msg"));
		assertEquals("1.png", r.getPic());
	}

	private ResultDO createResult() {
		ResultDO r = new ResultDO();
		r.setTid(1);
		r.setStatus(1);
		Date d = getTime(8, 1);
		r.setStartTime(d);
		r.setEndTime(d);
		Map<String, String> detail = new HashMap<>();
		detail.put("id", "1");
		r.setDetail(detail);
		r.setPic("1.png");
		return r;
	}

}
