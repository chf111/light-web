package up.light.web.service;

import java.util.List;

import up.light.web.entity.ProjectDO;

public interface IProjectService {

	/**
	 * 添加项目
	 * 
	 * @param proj
	 * @return
	 */
	int insertProject(ProjectDO proj);

	/**
	 * 修改项目
	 * 
	 * @param proj
	 * @return
	 */
	int updateProject(ProjectDO proj);

	/**
	 * 删除项目，同时删除该项目下的所有Test
	 * 
	 * @param id
	 * @return
	 */
	int deleteProject(int id);

	/**
	 * 查询所有项目
	 * 
	 * @return
	 */
	List<ProjectDO> selectAllProjects();

}
