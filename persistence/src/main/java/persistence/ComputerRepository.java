package persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import core.model.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Integer> {
	Optional<Computer> findByName(String name);
	List<Computer> findByNameContaining(String name);
	List<Computer> findByNameContaining(String name, Sort sort);
}
