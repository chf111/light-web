package up.light.web.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
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
import up.light.web.entity.FlowDO;
import up.light.web.entity.StepDO;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
@Rollback
public class IFlowDaoTest extends BaseTest {
	@Resource
	private IFlowDao dao;

	@DatabaseSetup(value = "flow/t_flow.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase(value = "flow/t_flow_insert_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testInsertFlow() {
		FlowDO f = new FlowDO();
		f.setMid(1);
		f.setInitScript("1.sql");
		f.setSteps(Arrays.asList(createStep(1), createStep(2)));
		int c = dao.insertFlow(f);
		assertEquals(2, c);
		assertEquals(1, f.getId());
	}

	@Test
	public void testUpdateFlow() {
		fail("Not yet implemented");
	}

	@DatabaseSetup(value = "flow/t_flow.xml")
	@ExpectedDatabase(value = "flow/t_flow_delete_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testDeleteFlow() {
		int c = dao.deleteFlow(1);
		assertEquals(2, c);
	}

	@DatabaseSetup(value = "flow/t_flow_step.xml")
	@Test
	public void testSelectFlowByModule() {
		FlowDO f = dao.selectFlowByModule(1);
		assertNotNull(f);
		assertEquals(1, f.getId());
		assertEquals("1.sql", f.getInitScript());
		assertEquals(1, f.getMid());
		List<StepDO> ss = f.getSteps();
		assertEquals(2, ss.size());
		StepDO s = ss.get(0);
		assertEquals(4620, s.getServiceId());
		assertEquals("/api/trade/ggt/ptyw/wtxd", s.getUrl());
		assertEquals(1, s.getType());
		assertEquals(1, s.getOrderNo());
		Map<String, String> m = s.getResultPaths();
		assertEquals(1, m.size());
		assertEquals("$.a", m.get("a"));
		assertNotNull(s.getInfo());
	}

	@DatabaseSetup(value = "flow/t_flow_step.xml")
	@Test
	public void testSelectFlowByPage() {
		List<FlowDO> fs = dao.selectFlowByPage();
		assertEquals(2, fs.size());
		FlowDO f = fs.get(1);
		assertEquals(2, f.getId());
		assertNull(f.getInitScript());
		assertEquals(2, f.getMid());
		List<StepDO> ss = f.getSteps();
		assertEquals(1, ss.size());
		StepDO s = ss.get(0);
		assertEquals(4624, s.getServiceId());
		assertEquals("/api/trade/rzrq/xgsg/yjsg", s.getUrl());
		assertEquals(1, s.getType());
		assertEquals(1, s.getOrderNo());
		Map<String, String> m = s.getResultPaths();
		assertEquals(1, m.size());
		assertEquals("$.a", m.get("a"));
		assertNotNull(s.getInfo());
	}

	private StepDO createStep(int serviceId) {
		StepDO s = new StepDO();
		s.setServiceId(serviceId);
		return s;
	}

}
