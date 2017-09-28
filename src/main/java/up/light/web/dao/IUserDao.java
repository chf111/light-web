package up.light.web.dao;

import java.util.List;

import up.light.web.entity.UserDO;

public interface IUserDao {

	int insertUser(UserDO user);

	int updateUser(UserDO user);

	int deleteUser(int id);

	UserDO selectUserById(int id);

	String selectPasswordById(int id);

	UserDO selectUserByUsername(String username);

	List<UserDO> selectAllUsers();

}
