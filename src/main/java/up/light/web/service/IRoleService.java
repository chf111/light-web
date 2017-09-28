package up.light.web.service;

import java.util.List;

import up.light.web.entity.ResourceDO;
import up.light.web.entity.RoleDO;

public interface IRoleService {

	/**
	 * 添加角色
	 * 
	 * @param role 角色信息
	 * @return 受影响的行数
	 */
	int insertRole(RoleDO role);

	/**
	 * 修改角色信息（只更新角色描述信息）
	 * 
	 * @param role
	 * @return 受影响的行数
	 */
	int updateRole(RoleDO role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return 受影响的行数
	 */
	int deleteRole(int id);

	/**
	 * 根据ID查询角色（不包含资源信息）
	 * 
	 * @param id
	 * @return
	 */
	RoleDO selectRoleById(int id);

	/**
	 * 查询所有角色（不包含资源信息）
	 * 
	 * @return
	 */
	List<RoleDO> selectAllRolesWithoutResources();

	/**
	 * 查询指定角色可访问的资源
	 * 
	 * @param id
	 * @return
	 */
	List<ResourceDO> selectResources(int id);

}
