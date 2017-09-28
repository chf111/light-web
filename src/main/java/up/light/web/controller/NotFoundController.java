package up.light.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import up.light.web.common.CommonResponse;

@RestController
public class NotFoundController {

	@RequestMapping("/404")
	public ResponseEntity<CommonResponse> notFound() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(CommonResponse.error(CommonResponse.CODE_NOT_FOUND, "资源未找到"));
	}

}
