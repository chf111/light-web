package up.light.web.service;

import java.util.List;

import up.light.web.entity.TestDO;

public interface ITestService {

	/**
	 * 添加测试
	 * 
	 * @param test
	 * @return -1：任务已满，其他：添加到执行队列的条数
	 */
	int insertAndRunTest(TestDO test);

	/**
	 * 删除测试，同时删除该项目下的所有Result
	 * 
	 * @param id 测试ID
	 * @return 受影响行数
	 */
	int deleteTest(int id);

	/**
	 * 删除项目下的所有测试，同时删除该项目下的所有Result
	 * 
	 * @param id 项目ID
	 * @return 受影响行数
	 */
	int deleteTestByProject(int pid);

	/**
	 * 查询测试（在调用此方法前调用{@link PageHelper#setPage(int, int)方法可实现分页}）
	 * 
	 * @param projectId 项目ID
	 * @param pageNo 当前页码
	 * @param pageSize 每页条数
	 * @return
	 */
	List<TestDO> selectTestByPage(int projectId, int pageNo, int pageSize);

}
