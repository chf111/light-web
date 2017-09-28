package up.light.web.authorize;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import up.light.web.service.IResourceService;

/**
 * 当访问一个url时返回这个url所需要的访问权限
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	// 访问对应URL所需要的角色
	private List<RoleResource> roleResources;

	@Resource
	private IResourceService resourceService;

	/**
	 * 返回访问URL所需要的角色
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest req = ((FilterInvocation) object).getRequest();
		RequestMatcher mathcer;

		for (RoleResource requiredRole : loadAllProtectedResource()) {
			mathcer = new AntPathRequestMatcher(getFixedUrlPattern(req, requiredRole.urlPattern), requiredRole.method);

			if (mathcer.matches(req)) {
				return requiredRole.roles;
			}
		}
		return Collections.emptyList();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	private List<RoleResource> loadAllProtectedResource() {
		if (roleResources == null) {
			roleResources = resourceService.selectAllRoleResources();
		}

		return roleResources;
	}

	/*
	 * 修正末尾的 "/"
	 */
	private String getFixedUrlPattern(HttpServletRequest request, String urlPattern) {
		String url = request.getServletPath();

		if (request.getPathInfo() != null) {
			url += request.getPathInfo();
		}

		if (url.endsWith("/") && !urlPattern.endsWith("/")) {
			return urlPattern + "/";
		}

		if (!url.endsWith("/") && urlPattern.endsWith("/")) {
			return urlPattern.substring(0, urlPattern.length() - 1);
		}

		return urlPattern;
	}

}
