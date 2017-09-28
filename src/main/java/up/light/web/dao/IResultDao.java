package up.light.web.dao;

import java.util.List;

import up.light.web.entity.ResultDO;

public interface IResultDao {

	int insertResult(ResultDO result);

	int updateResult(ResultDO result);

	int deleteResultByTest(int tid);

	List<ResultDO> selectResultByPage(int tid);

}
