package up.light.web.dao;

import static org.junit.Assert.*;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import up.light.web.BaseTest;
import up.light.web.entity.handler.ConfigCollector;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
@Rollback
public class IConfigDaoTest extends BaseTest {
	@Resource
	private IConfigDao dao;

	@Test
	public void testInsertConfigs() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteConfig() {
		fail("Not yet implemented");
	}

	@DatabaseSetup("config/t_config.xml")
	@Test
	public void testSelectConfig() {
		ConfigCollector collector = new ConfigCollector();
		dao.selectConfig(1, collector);
		Map<String, String> m = collector.getConfigs();
		assertEquals(2, m.size());
		assertEquals("a", m.get("a"));
		assertEquals("b", m.get("b"));
	}

}
