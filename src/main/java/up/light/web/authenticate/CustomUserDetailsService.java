package up.light.web.authenticate;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import up.light.web.entity.UserDO;
import up.light.web.service.IUserService;

/**
 * 从数据库中读取用户信息以校验前端传过来的登录信息是否正确
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Resource
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDO user = userService.selectUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Can't find user with name [" + username + "]");
		}

		Collection<GrantedAuthority> auths = Arrays.asList(new SimpleGrantedAuthority(user.getRole().getName()));
		return new User(user.getUsername(), user.getPassword(), user.isEnable(), true, true, true, auths);
	}

}
