package up.light.web.entity;

import java.util.List;

/**
 * 资源
 */
public class ResourceDO {
	private int id;
	private String method;
	private String urlPattern;
	private List<RoleDO> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	public List<RoleDO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDO> roles) {
		this.roles = roles;
	}

}
