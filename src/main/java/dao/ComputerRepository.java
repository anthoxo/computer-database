package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Integer> {

	@Override
	<S extends Computer> S save(S computer);

	@Override
	Optional<Computer> findById(Integer id);

	@Override
	void delete(Computer computer);

	@Override
	List<Computer> findAll();

	@Override
	List<Computer> findAll(Sort sort);

	List<Computer> findByName(String name);
	List<Computer> findByNameContaining(String name);
	List<Computer> findByNameContaining(String name, Sort sort);
}
