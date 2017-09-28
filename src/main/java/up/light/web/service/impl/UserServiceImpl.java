package up.light.web.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import up.light.web.dao.IUserDao;
import up.light.web.entity.UserDO;
import up.light.web.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao dao;
	@Resource
	private PasswordEncoder encoder;

	@Override
	public int insertUser(UserDO user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setLastTime(new Date());
		return dao.insertUser(user);
	}

	@Override
	public int updateUser(UserDO user) {
		user.setPassword(null);
		return dao.updateUser(user);
	}

	@Override
	public int deleteUser(int id) {
		if (id == 1)
			return 0;
		return dao.deleteUser(id);
	}

	@Override
	public UserDO selectUserById(int id) {
		return dao.selectUserById(id);
	}

	@Override
	public UserDO selectUserByUsername(String username) {
		return dao.selectUserByUsername(username);
	}

	@Override
	public List<UserDO> selectAllUsers() {
		return dao.selectAllUsers();
	}

	@Override
	public int updatePassword(int id, String oldPwd, String newPwd) {
		String pwd = dao.selectPasswordById(id);

		// 用户不存在
		if (pwd == null)
			return 0;

		// 原始密码错误
		if (!encoder.matches(oldPwd, pwd))
			return -1;

		UserDO user = new UserDO();
		user.setId(id);
		user.setPassword(encoder.encode(newPwd));
		user.setLastTime(new Date());
		return dao.updateUser(user);
	}

}
