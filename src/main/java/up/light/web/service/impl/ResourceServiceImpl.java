package up.light.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import up.light.web.authorize.RoleResource;
import up.light.web.entity.ResourceDO;
import up.light.web.entity.RoleDO;
import up.light.web.service.IResourceService;

@Service
public class ResourceServiceImpl implements IResourceService {

	@Override
	public int insertResource(ResourceDO res) {
		return 0;
	}

	@Override
	public int updateResource(ResourceDO res) {
		return 0;
	}

	@Override
	public int deleteResource(int id) {
		return 0;
	}

	@Override
	public ResourceDO selectResourceById(int id) {
		return null;
	}

	@Override
	public List<ResourceDO> selectAllResourcesWithoutRoles() {
		return null;
	}

	@Override
	public List<RoleResource> selectAllRoleResources() {
		return null;
	}

	@Override
	public List<RoleDO> selectRoles(int id) {
		return null;
	}

}
