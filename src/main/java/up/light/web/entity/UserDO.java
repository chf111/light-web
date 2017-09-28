package up.light.web.entity;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import up.light.web.controller.UserController.GroupCheckPassword;

/**
 * 用户
 */
public class UserDO {
	private int id;

	@NotNull(message = "用户名不能为空")
	@Length(min = 4, max = 16, message = "用户名长度必须为4~16位")
	private String username;

	@NotNull(message = "密码不能为空", groups = GroupCheckPassword.class)
	@Length(min = 6, max = 16, message = "密码长度必须为6~16位", groups = GroupCheckPassword.class)
	private String password;

	@NotNull(message = "昵称不能为空")
	@Length(min = 2, max = 8, message = "昵称长度必须为2~8位")
	private String nickname;

	private boolean enable;
	private Date lastTime;

	@Valid
	@NotNull(message = "用户角色不能为空")
	private RoleDO role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public RoleDO getRole() {
		return role;
	}

	public void setRole(RoleDO role) {
		this.role = role;
	}

}
