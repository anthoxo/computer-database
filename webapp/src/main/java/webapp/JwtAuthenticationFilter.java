package webapp;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import core.model.User;
import service.JwtService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtService jwtService;

	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			User user = (User)auth.getPrincipal();
			if (user != null) {
				if (!user.getToken().isEmpty() && jwtService.validateToken(user.getToken())) {
					// System.out.println("OK");
				} else {
					// SecurityContextHolder.getContext().setAuthentication(null);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
