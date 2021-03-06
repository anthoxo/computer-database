package webapp;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import core.model.User;
import service.JwtService;
import service.UserService;

public class AuthenticationFilter extends OncePerRequestFilter {

	private UserService userService;
	private JwtService jwtService;

	public AuthenticationFilter(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		SecurityContextHolder.getContext().setAuthentication(null);
		String token = getJwtFromRequest(request);

		Optional<Integer> id = jwtService.getUserIdByJwt(token);
		if (id.isPresent() && jwtService.validateToken(token)) {
			User user = (User) this.userService.loadUserById(id.get());
			user.setToken(token);
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && !bearerToken.trim().isEmpty() && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
