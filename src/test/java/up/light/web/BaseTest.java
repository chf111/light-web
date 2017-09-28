package up.light.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import up.light.web.authenticate.JwtTokenUtil;
import up.light.web.config.MybatisConfig;
import up.light.web.config.SpringMvcConfig;
import up.light.web.config.SpringSecurityConfig;

/*
 * 必须定义为抽象类，否则junit会报 no runnable methods 异常
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringSecurityConfig.class, SpringMvcConfig.class, MybatisConfig.class })
public abstract class BaseTest {
	protected static final String HEADER_NAME = "Authorization";
	protected static String TOKEN_ADMIN;
	protected static String TOKEN_ROLE;
	private static ObjectWriter writer = new ObjectMapper().writer();

	@Resource
	private JwtTokenUtil jwtTokenUtil;

	protected MockMvcBuilder mvcBuilder(WebApplicationContext cxt) {
		return MockMvcBuilders.webAppContextSetup(cxt).apply(SecurityMockMvcConfigurers.springSecurity());
	}

	protected String headerAdmin() {
		if (TOKEN_ADMIN == null) {
			UserDetails userDetails = getUserDetails("ADMIN");
			TOKEN_ADMIN = "Bearer " + jwtTokenUtil.createToken(userDetails);
		}
		return TOKEN_ADMIN;
	}

	protected String headerUser() {
		if (TOKEN_ROLE == null) {
			UserDetails userDetails = getUserDetails("USER");
			TOKEN_ROLE = "Bearer " + jwtTokenUtil.createToken(userDetails);
		}
		return TOKEN_ROLE;
	}

	protected String headerRefresh() {
		return jwtTokenUtil.createRefreshToken(getUserDetails("USER"));
	}

	protected String toJson(Object obj) throws Exception {
		return writer.writeValueAsString(obj);
	}

	/*
	 * 生成时间
	 */
	protected Date getTime(int month, int day) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2017, month - 1, day, 12, 0, 0);
		Date d = c.getTime();
		return d;
	}

	private UserDetails getUserDetails(String role) {
		return new User("unit", "", Arrays.asList(new SimpleGrantedAuthority(role)));
	}

}
