package persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import core.model.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {

	@Override
	Optional<Company> findById(Integer id);

	@Override
	List<Company> findAll();

	List<Company> findAllByOrderByNameAsc();
	List<Company> findAllByOrderByNameDesc();

	@Override
	void delete(Company company);
}
