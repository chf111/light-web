package up.light.web.dao;

import java.util.List;

import up.light.web.entity.ResourceDO;
import up.light.web.entity.RoleDO;

public interface IRoleDao {

	int insertRole(RoleDO role);

	int updateRole(RoleDO role);

	int deleteRole(int id);

	RoleDO selectRoleById(int id);

	List<RoleDO> selectAllRoles();

	List<ResourceDO> selectResources(int id);

}
