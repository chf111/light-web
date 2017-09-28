package up.light.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import up.light.web.entity.ResourceDO;
import up.light.web.entity.RoleDO;
import up.light.web.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Override
	public int insertRole(RoleDO role) {
		return 0;
	}

	@Override
	public int updateRole(RoleDO role) {
		return 0;
	}

	@Override
	public int deleteRole(int id) {
		return 0;
	}

	@Override
	public RoleDO selectRoleById(int id) {
		return null;
	}

	@Override
	public List<RoleDO> selectAllRolesWithoutResources() {
		return null;
	}

	@Override
	public List<ResourceDO> selectResources(int id) {
		return null;
	}

}
