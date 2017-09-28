package up.light.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.WebUtils;

import up.light.web.common.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@SuppressWarnings("deprecation")
	@ExceptionHandler({ org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException.class,
			HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
			HttpMediaTypeNotAcceptableException.class, MissingPathVariableException.class,
			MissingServletRequestParameterException.class, ServletRequestBindingException.class,
			ConversionNotSupportedException.class, TypeMismatchException.class, HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class, MethodArgumentNotValidException.class,
			MissingServletRequestPartException.class, BindException.class, NoHandlerFoundException.class,
			AsyncRequestTimeoutException.class, AuthenticationException.class })
	public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		if (ex instanceof org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return handleNoSuchRequestHandlingMethod(
					(org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException) ex, headers,
					status, request);
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
			return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, headers, status,
					request);
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
			return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, headers, status, request);
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
			return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, headers, status, request);
		} else if (ex instanceof MissingPathVariableException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingPathVariable((MissingPathVariableException) ex, headers, status, request);
		} else if (ex instanceof MissingServletRequestParameterException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, headers, status,
					request);
		} else if (ex instanceof ServletRequestBindingException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleServletRequestBindingException((ServletRequestBindingException) ex, headers, status, request);
		} else if (ex instanceof ConversionNotSupportedException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleConversionNotSupported((ConversionNotSupportedException) ex, headers, status, request);
		} else if (ex instanceof TypeMismatchException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleTypeMismatch((TypeMismatchException) ex, headers, status, request);
		} else if (ex instanceof HttpMessageNotReadableException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, headers, status, request);
		} else if (ex instanceof HttpMessageNotWritableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, headers, status, request);
		} else if (ex instanceof MethodArgumentNotValidException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMethodArgumentNotValid((MethodArgumentNotValidException) ex, headers, status, request);
		} else if (ex instanceof MissingServletRequestPartException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestPart((MissingServletRequestPartException) ex, headers, status, request);
		} else if (ex instanceof BindException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleBindException((BindException) ex, headers, status, request);
		} else if (ex instanceof NoHandlerFoundException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return handleNoHandlerFoundException((NoHandlerFoundException) ex, headers, status, request);
		} else if (ex instanceof AsyncRequestTimeoutException) {
			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
			return handleAsyncRequestTimeoutException((AsyncRequestTimeoutException) ex, headers, status, request);
		} else if (ex instanceof AuthenticationException) {
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			return handleAuthenticationException((AuthenticationException) ex, headers, status, request);
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("Unknown exception type: " + ex.getClass().getName());
			}
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleExceptionInternal(ex, CommonResponse.error(CommonResponse.CODE_INTERNAL_ERROR, "服务器内部错误"),
					headers, status, request);
		}
	}

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<Object>(body, headers, status);
	}

	@Deprecated
	protected ResponseEntity<Object> handleNoSuchRequestHandlingMethod(
			org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.error(CommonResponse.CODE_NOT_FOUND, "资源未找到"), headers,
				status, request);
	}

	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		if (!CollectionUtils.isEmpty(supportedMethods)) {
			headers.setAllow(supportedMethods);
		}
		return handleExceptionInternal(ex, CommonResponse.create(CommonResponse.CODE_NO_METHOD, "不支持该请求方法", null),
				headers, status, request);
	}

	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			headers.setAccept(mediaTypes);
		}

		return handleExceptionInternal(ex, CommonResponse.invalidParam("MIME类型错误"), headers, status, request);
	}

	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("无法创建对应MIME类型的响应"), headers, status, request);
	}

	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("参数不全"), headers, status, request);
	}

	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("参数不全"), headers, status, request);
	}

	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("参数绑定错误"), headers, status, request);
	}

	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("无法获取参数"), headers, status, request);
	}

	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("参数类型错误"), headers, status, request);
	}

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("无法获取参数"), headers, status, request);
	}

	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("无法写入参数"), headers, status, request);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("参数格式错误"), headers, status, request);
	}

	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.invalidParam("参数错误"), headers, status, request);
	}

	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, CommonResponse.error(CommonResponse.CODE_NOT_FOUND, "资源未找到"), headers,
				status, request);
	}

	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		if (webRequest instanceof ServletWebRequest) {
			ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
			HttpServletRequest request = servletRequest.getNativeRequest(HttpServletRequest.class);
			HttpServletResponse response = servletRequest.getNativeResponse(HttpServletResponse.class);
			if (response.isCommitted()) {
				if (logger.isErrorEnabled()) {
					logger.error("Async timeout for " + request.getMethod() + " [" + request.getRequestURI() + "]");
				}
				return null;
			}
		}

		return handleExceptionInternal(ex, CommonResponse.error(CommonResponse.CODE_INTERNAL_ERROR, "异步请求超时"), headers,
				status, webRequest);
	}

	protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, HttpHeaders headers,
			HttpStatus status, WebRequest webRequest) {

		return handleExceptionInternal(ex, CommonResponse.error(CommonResponse.CODE_UNAUTH, "认证失败"), headers, status,
				webRequest);
	}

}
