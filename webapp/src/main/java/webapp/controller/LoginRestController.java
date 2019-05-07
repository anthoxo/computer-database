package webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import binding.dto.UserDTO;
import binding.mapper.UserMapper;
import core.model.User;
import service.JwtService;
import service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginRestController {

	AuthenticationManager authenticationManager;
	UserService userService;
	JwtService jwtService;
	UserMapper userMapper;

	public LoginRestController(AuthenticationManager authenticationManager, UserService userService,
			JwtService jwtService, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtService = jwtService;
		this.userMapper = userMapper;
	}

	@PostMapping("")
	public ResponseEntity<UserDTO> postLogin(@RequestBody UserDTO userDTO) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
		if (auth.isAuthenticated()) {
			User user = (User) auth.getPrincipal();
			user.setToken(this.jwtService.generateToken(auth));
			return new ResponseEntity<UserDTO>(this.userMapper.createDTO(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
		}
	}
}
