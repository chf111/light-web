package up.light.web.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import up.light.web.BaseTest;
import up.light.web.entity.RoleDO;
import up.light.web.entity.UserDO;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
@Rollback
public class IUserDaoTest extends BaseTest {
	@Resource
	private IUserDao dao;

	@DatabaseSetup(value = "user/t_user.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase(value = "user/t_user_insert_exp.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@Test
	public void testInsertUser() {
		UserDO user = createUser();
		int c = dao.insertUser(user);
		assertEquals(1, c);
		assertTrue(user.getId() > 0);
	}

	@DatabaseSetup(value = "user/t_user.xml")
	@ExpectedDatabase(value = "user/t_user_update_exp.xml", table = "t_user")
	@Test
	public void testUpdateUserNoPwd() {
		// id:2, username:unit, password:null, nickname:unit, enable:0,
		// last_time:2017-01-01 12:00:00, rid:1
		UserDO user = createUser();
		user.setPassword(null);
		user.getRole().setId(1);
		int c = dao.updateUser(user);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "user/t_user.xml")
	@ExpectedDatabase(value = "user/t_user_update_pwd_exp.xml", table = "t_user")
	@Test
	public void testUpdateUserWithPwd() {
		// id:2, username:unit, password:unit, nickname:unit, enable:0,
		// last_time:2017-12-25 12:00:00, rid:1
		UserDO user = createUser();
		user.setLastTime(getTime(12, 25));
		user.getRole().setId(1);
		int c = dao.updateUser(user);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "user/t_user.xml")
	@ExpectedDatabase(value = "user/t_user_delete_exp.xml", table = "t_user")
	@Test
	public void testDeleteUser() {
		int c = dao.deleteUser(2);
		assertEquals(1, c);
	}

	@DatabaseSetup(value = "user/t_user.xml")
	@Test
	public void testSelectUserById() {
		UserDO u = dao.selectUserById(1);
		assertNotNull(u);
		assertEquals(1, u.getId());
		assertEquals("light", u.getUsername());
		assertNull(u.getPassword());
		assertEquals("程序猿", u.getNickname());
		assertEquals(true, u.isEnable());
		assertEquals(getTime(8, 1), u.getLastTime());
		assertEquals(1, u.getRole().getId());

		u = dao.selectUserById(2);
		assertNotNull(u);
		assertEquals(2, u.getId());
		assertEquals("test", u.getUsername());
		assertNull(u.getPassword());
		assertEquals("普通测试用户", u.getNickname());
		assertEquals(true, u.isEnable());
		assertEquals(getTime(8, 1), u.getLastTime());
		assertEquals(4, u.getRole().getId());

		assertNull(dao.selectPasswordById(1000));
	}

	@DatabaseSetup(value = "user/t_user.xml")
	@Test
	public void testSelectPasswordById() {
		assertEquals("$2a$10$iseIarnUOLvG0rYRdTW8DegosXXflrNJQh694tH92q51Qd2DtC3OS", dao.selectPasswordById(1));
		assertEquals("$2a$10$7EVUgpBDnxWcAX09Z/g9/.uFrUziIO.o7lxWi0XfTAmlbtSERkfYy", dao.selectPasswordById(2));
		assertNull(dao.selectPasswordById(1000));
	}

	@DatabaseSetup(value = "user/t_user.xml")
	@Test
	public void testSelectUserByUsername() {
		// exist
		UserDO u = dao.selectUserByUsername("test");
		assertNotNull(u);
		assertEquals(0, u.getId());
		assertEquals("test", u.getUsername());
		assertEquals("$2a$10$7EVUgpBDnxWcAX09Z/g9/.uFrUziIO.o7lxWi0XfTAmlbtSERkfYy", u.getPassword());
		assertNull(u.getNickname());
		assertEquals(true, u.isEnable());
		assertEquals(getTime(8, 1), u.getLastTime());
		assertEquals("USER", u.getRole().getName());

		// not exist
		assertNull(dao.selectUserByUsername("unit"));
	}

	@DatabaseSetup(value = "user/t_user.xml")
	@Test
	public void testSelectAllUsers() {
		List<UserDO> us = dao.selectAllUsers();
		assertEquals(1, us.size());

		UserDO u = us.get(0);
		assertEquals(2, u.getId());
		assertEquals("test", u.getUsername());
		assertNull(u.getPassword());
		assertEquals("普通测试用户", u.getNickname());
		assertEquals(true, u.isEnable());
		assertEquals(getTime(8, 1), u.getLastTime());
		assertEquals(4, u.getRole().getId());
	}

	/*
	 * 创建user实例
	 */
	private UserDO createUser() {
		// id:2, username:unit, password:unit, nickname:unit, enable:0,
		// last_time:2017-12-25 12:00:00, rid:1
		UserDO user = new UserDO();
		user.setId(2);
		user.setUsername("unit");
		user.setPassword("unit");
		user.setNickname("unit");
		user.setEnable(false);
		user.setLastTime(getTime(1, 1));
		RoleDO r = new RoleDO();
		r.setId(4);
		user.setRole(r);
		return user;
	}

}
