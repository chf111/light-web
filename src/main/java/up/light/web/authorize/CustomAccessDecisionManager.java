package up.light.web.authorize;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		String needRole, haveRole;
		Collection<? extends GrantedAuthority> auths = authentication.getAuthorities();

		for (ConfigAttribute config : configAttributes) {
			needRole = config.getAttribute();
			for (GrantedAuthority auth : auths) {
				haveRole = auth.getAuthority();

				// developer 直接放行
				if ("DEVELOPER".equals(haveRole))
					return;

				if (needRole.equals(haveRole))
					return;
			}
		}
		throw new AccessDeniedException("no right");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
