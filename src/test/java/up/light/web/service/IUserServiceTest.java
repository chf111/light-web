package up.light.web.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import up.light.web.BaseTest;
import up.light.web.dao.IUserDao;
import up.light.web.entity.UserDO;

public class IUserServiceTest extends BaseTest {
	@InjectMocks
	@Resource
	private IUserService srv;

	@Resource
	private IUserDao dao;

	@Mock(name = "dao")
	private IUserDao mockDao;

	private UserDO user = new UserDO();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(mockDao.insertUser(any())).thenReturn(1);
		when(mockDao.updateUser(any())).thenReturn(1);
		when(mockDao.deleteUser(anyInt())).thenReturn(1);
		user.setPassword("unit");
	}

	@Test
	public void testInsertUser() {
		assertEquals(1, srv.insertUser(user));
	}

	@Test
	public void testUpdateUser() {
		assertEquals(1, srv.updateUser(user));
	}

	@Test
	public void testDeleteUser() {
		assertEquals(0, srv.deleteUser(1));
		assertEquals(1, srv.deleteUser(2));
	}

	@Test
	public void testSelectUserById() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectUserByUsername() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectAllUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePassword() {
		fail("Not yet implemented");
	}

}
