package up.light.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import up.light.web.common.CommonResponse;
import up.light.web.entity.UserDO;
import up.light.web.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Resource
	private IUserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CommonResponse> addUser(
			@RequestBody @Validated({ GroupCheckPassword.class, Default.class }) UserDO user, BindingResult result) {
		if (result.hasErrors()) {
			return invalidParam(result);
		}
		if (userService.selectUserByUsername(user.getUsername()) != null) {
			return ResponseEntity.badRequest().body(CommonResponse.invalidParam("用户已存在"));
		}
		if (userService.insertUser(user) > 0) {
			user.setPassword(null);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(CommonResponse.success("创建成功", user));
		}
		return ResponseEntity.badRequest().body(CommonResponse.error(CommonResponse.CODE_INTERNAL_ERROR, "创建失败"));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CommonResponse> updateUser(@RequestBody @Validated UserDO user, BindingResult result,
			@PathVariable int id) {
		// 使用url中的id
		user.setId(id);

		if (result.hasErrors()) {
			return invalidParam(result);
		}
		if (userService.updateUser(user) > 0)
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(CommonResponse.success("更新成功", null));
		return noSuchUser();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CommonResponse> deleteUser(@PathVariable int id) {
		if (userService.deleteUser(id) > 0)
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body(CommonResponse.success("删除成功", null));
		return noSuchUser();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> selectAllUsers() {
		return ResponseEntity.ok(CommonResponse.success("查询成功", userService.selectAllUsers()));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> selectUser(@PathVariable int id) {
		return ResponseEntity.ok(CommonResponse.success("查询成功", userService.selectUserById(id)));
	}

	@RequestMapping(value = "/{id}/password", method = RequestMethod.POST)
	public ResponseEntity<CommonResponse> updatePassword(@PathVariable int id, @RequestParam("oldPwd") String oldPwd,
			@RequestParam("newPwd") String newPwd) {
		if (newPwd.length() < 6 || newPwd.length() > 16)
			return ResponseEntity.badRequest().body(CommonResponse.invalidParam("密码长度必须为6~16位"));

		int status = userService.updatePassword(id, oldPwd, newPwd);

		if (status < 0) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(CommonResponse.error(CommonResponse.CODE_UNAUTH, "原始密码错误"));
		} else if (status == 0) {
			return noSuchUser();
		}
		return ResponseEntity.ok(CommonResponse.success("修改成功", null));
	}

	private ResponseEntity<CommonResponse> invalidParam(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError err : result.getFieldErrors()) {
			errors.put(err.getField(), err.getDefaultMessage());
		}
		return ResponseEntity.badRequest()
				.body(CommonResponse.create(CommonResponse.CODE_INVALID_PARAM, "无效参数", errors));
	}

	private ResponseEntity<CommonResponse> noSuchUser() {
		return ResponseEntity.badRequest().body(CommonResponse.invalidParam("用户不存在"));
	}

	/*
	 * 参数校验分组接口
	 */
	public interface GroupCheckPassword {
	}

}
