package up.light.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import up.light.web.authenticate.JwtResponse;
import up.light.web.authenticate.JwtTokenUtil;
import up.light.web.common.CommonResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<CommonResponse> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		// authenticate
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(upToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// create tokens
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtTokenUtil.createToken(userDetails);
		String refreshToken = jwtTokenUtil.createRefreshToken(userDetails);

		// create response
		JwtResponse jwtResp = new JwtResponse();
		jwtResp.setToken(token);
		jwtResp.setRefreshToken(refreshToken);
		CommonResponse resp = CommonResponse.success("登录成功", jwtResp);
		return ResponseEntity.ok(resp);
	}

	@RequestMapping(value = "refresh", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> refresh(HttpServletRequest request) {
		// refreshToken: <refreshToken>
		String refreshToken = request.getHeader("refreshToken");

		if (refreshToken == null || refreshToken.length() == 0) {
			// invalid header
			CommonResponse resp = CommonResponse.error(CommonResponse.CODE_INVALID_PARAM, "header中无token");
			return ResponseEntity.badRequest().body(resp);
		}

		try {
			String token = jwtTokenUtil.refreshToken(refreshToken);
			CommonResponse resp = CommonResponse.success("刷新成功", token);
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			// invalid token
			CommonResponse resp = CommonResponse.error(CommonResponse.CODE_INVALID_PARAM, "token无效");
			return ResponseEntity.badRequest().body(resp);
		}
	}

}
