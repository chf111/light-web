package up.light.web.dao;

import java.util.List;

import up.light.web.entity.ResourceDO;
import up.light.web.entity.RoleDO;

public interface IResourceDao {

	int insertResource(ResourceDO res);

	int updateResource(ResourceDO res);

	int deleteResource(int id);

	ResourceDO selectResourceById(int id);

	List<ResourceDO> selectAllResources();

	List<ResourceDO> selectAllResourcesWithRole();

	List<RoleDO> selectRoles(int id);

}
