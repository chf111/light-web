package up.light.web.service;

import java.util.List;

import up.light.web.entity.ResultDO;

public interface IResultService {

	int insertResult(ResultDO result);

	int updateResult(ResultDO result);

	int deleteResultByTest(int tid);

	List<ResultDO> selectResultByPage(int tid);

}
