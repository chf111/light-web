package up.light.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import up.light.web.dao.IProjectDao;
import up.light.web.dao.ITestDao;
import up.light.web.entity.ProjectDO;
import up.light.web.service.IProjectService;

@Service
public class ProjectServiceImpl implements IProjectService {
	@Resource
	private IProjectDao projDao;
	@Resource
	private ITestDao testDao;

	@Override
	public int insertProject(ProjectDO proj) {
		return projDao.insertProject(proj);
	}

	@Override
	public int updateProject(ProjectDO proj) {
		return projDao.updateProject(proj);
	}

	@Transactional
	@Override
	public int deleteProject(int id) {
		int c = projDao.deleteProject(id);
		// 删除该项目对应的多有Test
		testDao.deleteTestByProject(id);
		return c;
	}

	@Override
	public List<ProjectDO> selectAllProjects() {
		return projDao.selectAllProjects();
	}

}
