package up.light.web.service;

import java.util.List;

import up.light.web.authorize.RoleResource;
import up.light.web.entity.ResourceDO;
import up.light.web.entity.RoleDO;

public interface IResourceService {

	/**
	 * 
	 * @param res
	 * @return
	 */
	int insertResource(ResourceDO res);

	/**
	 * 
	 * @param res
	 * @return
	 */
	int updateResource(ResourceDO res);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteResource(int id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	ResourceDO selectResourceById(int id);

	/**
	 * 
	 * @return
	 */
	List<ResourceDO> selectAllResourcesWithoutRoles();

	/**
	 * 
	 * @return
	 */
	List<RoleResource> selectAllRoleResources();

	/**
	 * 
	 * @param id
	 * @return
	 */
	List<RoleDO> selectRoles(int id);

}
