package up.light.web.dao;

import java.util.List;

import up.light.web.entity.ProjectDO;

public interface IProjectDao {

	int insertProject(ProjectDO proj);

	int updateProject(ProjectDO proj);

	int deleteProject(int id);

	List<ProjectDO> selectAllProjects();

}
