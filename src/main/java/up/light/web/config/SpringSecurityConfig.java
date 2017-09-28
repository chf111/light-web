package up.light.web.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import up.light.web.authenticate.CustomUserDetailsService;
import up.light.web.authenticate.JwtTokenFilter;
import up.light.web.authorize.CustomAccessDecisionManager;
import up.light.web.common.CommonResponse;

@Configuration
@EnableWebSecurity
@ComponentScan({ "up.light.web.authenticate", "up.light.web.authorize" })
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource
	private JwtTokenFilter jwtTokenFilter;
	@Resource
	private CustomUserDetailsService userDetailsService;
	@Resource
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	@Resource
	private PasswordEncoder passwordEncoder;

	private String unAuthMsg;
	private String noAccessMsg;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 添加过滤器
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				// 禁用CSRF
				.csrf().disable()
				//
				.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						setResponse(response, HttpServletResponse.SC_FORBIDDEN, getNoAccessMsg());
					}
				}).authenticationEntryPoint(new AuthenticationEntryPoint() {
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException authException) throws IOException, ServletException {
						setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, getUnAuthMsg());
					}
				}).and()
				// 基于token，不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 允许访问静态资源及auth
				.authorizeRequests().antMatchers("/auth/**", "/404", "/static/**", "/*.html").permitAll()
				// 其他URL需要认证
				.anyRequest().authenticated()
				// 动态权限控制
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
						fsi.setAccessDecisionManager(new CustomAccessDecisionManager());
						fsi.setSecurityMetadataSource(securityMetadataSource);
						return fsi;
					}
				}).and()
				// 禁用缓存
				.headers().cacheControl();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private void setResponse(HttpServletResponse response, int status, String msg) {
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		PrintWriter w = null;
		try {
			w = response.getWriter();
			w.println(msg);
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (w != null)
				w.close();
		}

	}

	private String getUnAuthMsg() {
		if (unAuthMsg == null) {
			unAuthMsg = obj2json(CommonResponse.error(CommonResponse.CODE_UNAUTH, "认证无效"));
		}
		return unAuthMsg;
	}

	private String getNoAccessMsg() {
		if (noAccessMsg == null) {
			noAccessMsg = obj2json(CommonResponse.error(CommonResponse.CODE_NO_ACCESS, "无权访问"));
		}
		return noAccessMsg;
	}

	private String obj2json(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer();
		String ret = null;
		try {
			ret = writer.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
