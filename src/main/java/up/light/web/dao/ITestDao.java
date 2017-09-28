package up.light.web.dao;

import java.util.List;

import up.light.web.entity.TestDO;

public interface ITestDao {

	int insertTest(TestDO test);

	int deleteTest(int id);

	int deleteTestByProject(int pid);

	List<TestDO> selectTestByPage(int projectId);

}
