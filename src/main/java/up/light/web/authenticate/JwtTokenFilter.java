package up.light.web.authenticate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import up.light.web.entity.UserDO;

/**
 * 根据token认证用户
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	@Resource
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Authorization: Bearer <refreshToken>
		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			UserDO user = jwtTokenUtil.getUserFromToken(token);

			if (user != null) {
				Collection<GrantedAuthority> auths = Arrays
						.asList(new SimpleGrantedAuthority(user.getRole().getName()));
				UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(
						user.getUsername(), user.getPassword(), auths);
				// add token as details
				upToken.setDetails(token);
				SecurityContextHolder.getContext().setAuthentication(upToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
