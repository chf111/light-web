package up.light.web.entity;

import java.util.List;

import javax.validation.constraints.Min;

/**
 * 角色
 */
public class RoleDO {
	@Min(value = 1, message = "角色ID必须大于0")
	private int id;
	private String name;
	private String description;
	private List<ResourceDO> resources;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ResourceDO> getResources() {
		return resources;
	}

	public void setResources(List<ResourceDO> resources) {
		this.resources = resources;
	}

}
