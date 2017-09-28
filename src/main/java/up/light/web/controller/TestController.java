package up.light.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import up.light.web.common.CommonResponse;

/**
 * 供测试权限用
 */
@RestController
public class TestController {

	@RequestMapping(value = "/test/admin", method = RequestMethod.GET)
	public CommonResponse admin() {
		return CommonResponse.success("初始状态下只能ADMIN访问", null);
	}

	@RequestMapping(value = "/test/user", method = RequestMethod.POST)
	public CommonResponse userPost() {
		return CommonResponse.success("初始状态下只能ADMIN访问", null);
	}

	@RequestMapping(value = "/test/user", method = RequestMethod.GET)
	public CommonResponse userGet() {
		return CommonResponse.success("初始状态下ADMIN、USER均可访问", null);
	}

}
