package persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import core.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsernameOrEmail(String usernameOrEmail1, String usernameOrEmail2);
	Optional<User> findByEmailAndPassword(String email, String password);
}
