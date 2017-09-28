package up.light.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import up.light.web.dao.IResultDao;
import up.light.web.entity.ResultDO;
import up.light.web.service.IResultService;

@Service
public class ResultServiceImpl implements IResultService {
	@Resource
	private IResultDao dao;

	@Override
	public int insertResult(ResultDO result) {
		return dao.insertResult(result);
	}

	@Override
	public int updateResult(ResultDO result) {
		return dao.updateResult(result);
	}

	@Override
	public int deleteResultByTest(int tid) {
		return dao.deleteResultByTest(tid);
	}

	@Override
	public List<ResultDO> selectResultByPage(int tid) {
		return dao.selectResultByPage(tid);
	}

}
