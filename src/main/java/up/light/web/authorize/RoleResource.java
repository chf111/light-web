package up.light.web.authorize;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;

/**
 * 访问某一资源所需要的角色
 */
public class RoleResource {
	public final String method;
	public final String urlPattern;
	public final Collection<ConfigAttribute> roles;

	public RoleResource(String method, String urlPattern, Collection<ConfigAttribute> roles) {
		this.method = method;
		this.urlPattern = urlPattern;
		this.roles = roles;
	}

}
