package up.light.web.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
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
import up.light.web.entity.RowDO;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
@Rollback
public class IRowDaoTest extends BaseTest {
	@Resource
	private IRowDao dao;

	@DatabaseSetup(value = "row/t_row.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase(value = "row/t_row_insert_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testInsertRow() {
		RowDO r = new RowDO();
		r.setMid(1);
		Map<String, List<String>> m = new HashMap<>();
		m.put("col", Arrays.asList("1", "2"));
		r.setValues(m);
		int c = dao.insertRow(r);
		assertEquals(1, c);
		assertTrue(r.getId() > 0);
	}

	@DatabaseSetup(value = "row/t_row.xml")
	@ExpectedDatabase(value = "row/t_row_update_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testUpdateRow() {
		RowDO r = new RowDO();
		r.setId(2);
		r.setMid(1);
		Map<String, List<String>> m = new HashMap<>();
		m.put("col", Arrays.asList("1", "2"));
		r.setValues(m);
		int c = dao.updateRow(r);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "row/t_row.xml")
	@ExpectedDatabase(value = "row/t_row_delete_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testDeleteRow() {
		int c = dao.deleteRow(1, 1);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "row/t_row.xml")
	@Test
	public void testSelectRowByPage() {
		List<RowDO> rs = dao.selectRowByPage(1);
		assertEquals(2, rs.size());
		RowDO r = rs.get(0);
		assertEquals(1, r.getId());
		assertEquals(1, r.getMid());
		Map<String, List<String>> m = r.getValues();
		assertEquals(1, m.size());
		List<String> v = m.get("col");
		assertNotNull(v);
		assertEquals(2, v.size());
		assertEquals("1", v.get(0));
		assertEquals("2", v.get(1));
	}

	@DatabaseSetup(value = "row/t_row.xml")
	@Test
	public void testForEachRow() {
		dao.forEachRow(1, new ResultHandler<RowDO>() {
			int i;

			@Override
			public void handleResult(ResultContext<? extends RowDO> resultContext) {
				++i;
				RowDO r = resultContext.getResultObject();
				assertEquals(i, r.getId());
				assertEquals(1, r.getMid());
				Map<String, List<String>> m = r.getValues();
				assertNotNull(m);
				if (i == 1) {
					assertEquals(1, m.size());
					List<String> v = m.get("col");
					assertNotNull(v);
					assertEquals(2, v.size());
					assertEquals("1", v.get(0));
					assertEquals("2", v.get(1));
				} else {
					assertEquals(0, m.size());
				}
			}
		});
	}

}
