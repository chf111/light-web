package up.light.web.service;

import java.util.List;

import up.light.web.entity.UserDO;

public interface IUserService {

	/**
	 * 添加用户
	 * 
	 * @param user 用户信息
	 * @return 受影响的行数
	 */
	int insertUser(UserDO user);

	/**
	 * 修改用户信息（不更新用户ID、用户名、密码） 修改密码使用{@link #updatePassword(int, String, String)}
	 * 
	 * @param user 用户信息
	 * @return 受影响的行数
	 */
	int updateUser(UserDO user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return 受影响的行数
	 */
	int deleteUser(int id);

	/**
	 * 根据ID查询用户（不包含密码）
	 * 
	 * @param id
	 * @return
	 */
	UserDO selectUserById(int id);

	/**
	 * 根据用户名查询用户（包含密码，仅用于登录认证）
	 * 
	 * @param username
	 * @return
	 */
	UserDO selectUserByUsername(String username);

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	List<UserDO> selectAllUsers();

	/**
	 * 修改密码
	 * 
	 * @param id 用户ID
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return -1：原密码错误，其他：受影响的行数
	 */
	int updatePassword(int id, String oldPwd, String newPwd);

}
