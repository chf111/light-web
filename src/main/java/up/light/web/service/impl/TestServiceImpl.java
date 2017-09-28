package up.light.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import up.light.web.common.Constants;
import up.light.web.common.ConvertUtil;
import up.light.web.common.PageHelper;
import up.light.web.dao.IConfigDao;
import up.light.web.dao.IFlowDao;
import up.light.web.dao.IModuleDao;
import up.light.web.dao.IResultDao;
import up.light.web.dao.IRowDao;
import up.light.web.dao.ITestDao;
import up.light.web.entity.FlowDO;
import up.light.web.entity.ModuleDO;
import up.light.web.entity.TestDO;
import up.light.web.entity.handler.ConfigCollector;
import up.light.web.http.HttpTaskHandler;
import up.light.web.http.PausableThreadPoolExecutor;
import up.light.web.http.ThreadPoolHolder;
import up.light.web.service.ITestService;

@Service
public class TestServiceImpl implements ITestService {
	@Resource
	private IConfigDao configDao;
	@Resource
	private ITestDao testDao;
	@Resource
	private IModuleDao moduleDao;
	@Resource
	private IRowDao rowDao;
	@Resource
	private IFlowDao flowDao;
	@Resource
	private IResultDao resultDao;

	@Transactional
	@Override
	public int insertAndRunTest(TestDO test) {
		if(ThreadPoolHolder.isFull())
			return -1;

		test.setFinish(false);
		test.setCreateTime(new Date());
		int c = testDao.insertTest(test);

		// create ThreadPool
		ThreadPoolExecutor pool = createThreadPool(test.getPid());
		ThreadPoolHolder.setThreadPool(test.getId(), pool);

		// add tasks
		HttpTaskHandler rowHandler = new HttpTaskHandler(pool);

		try {
			List<ModuleDO> modules = getRunModules(test);

			for (ModuleDO module : modules) {
				FlowDO flow = flowDao.selectFlowByModule(module.getId());
				rowHandler.buildFlow(flow);
				// create task for each row and add into pool
				rowDao.forEachRow(module.getId(), rowHandler);
			}
		} catch (Exception e) {
			ThreadPoolHolder.remove(test.getId());
			throw e;
		}finally {
			c = 0;
		}
		return c;
	}

	@Transactional
	@Override
	public int deleteTest(int id) {
		int c = testDao.deleteTest(id);
		resultDao.deleteResultByTest(id);
		return c;
	}

	@Override
	public int deleteTestByProject(int pid) {
		return testDao.deleteTestByProject(pid);
	}

	@Override
	public List<TestDO> selectTestByPage(int projectId, int pageNo, int pageSize) {
		PageHelper.setPage(pageNo, pageSize);
		return testDao.selectTestByPage(projectId);
	}

	private List<ModuleDO> getRunModules(TestDO test) {
		int dsid;
		if ((dsid = test.getDsid()) > 0) {
			return moduleDao.selectModulesByDataSet(dsid);
		}
		return moduleDao.selectModulesByProject(test.getPid());
	}

	private ThreadPoolExecutor createThreadPool(int pid) {
		ConfigCollector collector = new ConfigCollector();
		configDao.selectConfig(1, collector);
		Map<String, String> configs = collector.getConfigs();
		int threadCount = ConvertUtil.parseIntOrDefault(configs.get("threadCount"), Constants.THREAD_COUNT);
		return new PausableThreadPoolExecutor(threadCount, threadCount, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>());
	}

}
