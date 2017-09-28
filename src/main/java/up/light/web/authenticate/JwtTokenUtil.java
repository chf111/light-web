package up.light.web.authenticate;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import up.light.web.entity.RoleDO;
import up.light.web.entity.UserDO;

@Component
public class JwtTokenUtil {
	private static final String KEY_ROLE = "role";
	private static final String TYPE_TOKEN = "access";
	private static final String TYPE_REFRESH = "refresh";
	private static final String KEY_TYPE = "type";
	private static final int JWT_TTL = 60 * 60 * 1000;// 1 hours
	private static final int JWT_REFRESH_TTL = 8 * 60 * 60 * 1000;// 8 hours
	// bGlnaHRfand0
	private static final String SIGN_KEY = Base64.getEncoder().encodeToString("light_jwt".getBytes());

	/**
	 * 创建 access token
	 * 
	 * @param userDetails
	 * @return
	 */
	public String createToken(UserDetails userDetails) {
		return generateToken(userDetails, JWT_TTL, TYPE_TOKEN);
	}

	/**
	 * 创建 refresh token
	 * 
	 * @param userDetails
	 * @return
	 */
	public String createRefreshToken(UserDetails userDetails) {
		return generateToken(userDetails, JWT_REFRESH_TTL, TYPE_REFRESH);
	}

	/**
	 * 刷新 token，要求类型refresh token，错误的类型会抛出IllegalArgumentException
	 * 
	 * @param refreshToken
	 * @return
	 */
	public String refreshToken(String refreshToken) {
		Claims claims = getClaimsFromToken(refreshToken, TYPE_REFRESH);
		claims.setIssuedAt(new Date()).setExpiration(generateExpirationDate(JWT_TTL));
		return createToken(claims);
	}

	/**
	 * 从token中获取用户信息，要求类型access token，错误的类型会抛出IllegalArgumentException
	 * 
	 * @param token
	 * @return
	 */
	public UserDO getUserFromToken(String token) {
		UserDO user;
		try {
			Claims claims = getClaimsFromToken(token, TYPE_TOKEN);
			String username = claims.getSubject();
			String roleStr = claims.get(KEY_ROLE, String.class);

			user = new UserDO();
			user.setUsername(username);
			user.setPassword("");
			user.setEnable(true);
			RoleDO role = new RoleDO();
			role.setName(roleStr);
			user.setRole(role);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}

	/*
	 * generate token from UserDetails
	 */
	private String generateToken(UserDetails userDetails, int expiration, String type) {
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(generateExpirationDate(expiration));
		Collection<? extends GrantedAuthority> auths = userDetails.getAuthorities();
		claims.put(KEY_ROLE, auths.iterator().next().getAuthority());
		claims.put(KEY_TYPE, type);
		return createToken(claims);
	}

	/*
	 * generate token
	 */
	private String createToken(Claims claims) {
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SIGN_KEY).compact();
	}

	/*
	 * parse token
	 */
	private Claims getClaimsFromToken(String token, String requiredType) {
		Claims claims = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(token).getBody();
		String type = claims.get(KEY_TYPE, String.class);
		if (!requiredType.equals(type)) {
			throw new IllegalArgumentException();
		}
		return claims;
	}

	/*
	 * generate expiration date
	 */
	private Date generateExpirationDate(int exp) {
		return new Date(System.currentTimeMillis() + exp);
	}

}
