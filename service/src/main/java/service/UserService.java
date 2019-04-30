package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import core.model.User;
import persistence.UserRepository;
import persistence.exception.ItemBadCreatedException;

@Service
public class UserService implements UserDetailsService {

	UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void createUser(User user) throws ItemBadCreatedException {
		try {
			this.userRepository.save(user);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemBadCreatedException("userService");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException(""));
		return user;
	}

	public UserDetails loadUserById(int id) throws UsernameNotFoundException {
		User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
		return user;
	}

}
