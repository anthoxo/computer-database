package service;

import java.util.Date;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import core.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtService {

	public static final String SECRET_KEY = "secret.key";
	public static final String EXPIRED_TIME = "expired.time";

	private String jwtSecret = "";
	private int jwtExpirationInMs = 1;

	private Logger logger = LoggerFactory.getLogger(JwtService.class);

	public JwtService(Properties jwtProperties) {
		this.jwtSecret = jwtProperties.getProperty(SECRET_KEY);
		this.jwtExpirationInMs = Integer.valueOf(jwtProperties.getProperty(EXPIRED_TIME));
	}

	public String generateToken(Authentication auth) {
		User user = (User) auth.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder().setSubject(Integer.toString(user.getId())).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parse(token);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}

	public Optional<Integer> getUserIdByJwt(String jwt) {
		if (jwt == null || jwt.trim().isEmpty()) {
			return Optional.empty();
		} else {
			Claims claims = (Claims) Jwts.parser().setSigningKey(jwtSecret).parse(jwt).getBody();
			String result = claims.getSubject();
			try {
				return Optional.of(Integer.valueOf(result));
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				return Optional.empty();
			}
		}
	}
}
