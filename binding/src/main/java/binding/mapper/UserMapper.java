package binding.mapper;

import org.springframework.stereotype.Component;

import binding.dto.UserDTO;
import core.model.User;

@Component
public class UserMapper {
	private UserMapper() {

	}

	public UserDTO createDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setUsername(user.getUsername());
		userDTO.setRole(user.getRole());
		userDTO.setToken(user.getToken());
		return userDTO;
	}

	/**
	 * Convert a DTO to its model.
	 *
	 * @param cDTO The DTO we want to transform.
	 * @return A Company model.
	 */
	public User createEntity(UserDTO userDTO) {
		return (new User.Builder())
				.withId(userDTO.getId())
				.withEmail(userDTO.getEmail())
				.withHashPassword(userDTO.getPassword())
				.withUsername(userDTO.getUsername())
				.withRole(userDTO.getRole())
				.withToken(userDTO.getToken())
				.build();
	}

}
