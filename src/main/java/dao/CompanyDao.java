package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import exception.DAOException;
import mapper.CompanyMapper;
import model.Company;

@Repository
public class CompanyDao {

	static final String REQUEST_GET_BY_ID = "SELECT id,name FROM company WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT id,name FROM company";
	static final String REQUEST_GET_ALL_ORDER_BY_NAME = "SELECT id,name FROM company ORDER BY name";
	static final String REQUEST_GET_BY_NAME = "SELECT id,name FROM company WHERE name = ?";
	static final String REQUEST_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ?";
	static final String REQUEST_DELETE_COMPANY_BY_ID = "DELETE FROM company WHERE id = ?";

	JdbcTemplate jdbcTemplate;
	CompanyMapper companyMapper;

	private CompanyDao(JdbcTemplate jdbcTemplate, CompanyMapper companyMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.companyMapper = companyMapper;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(readOnly = true)
	public Optional<Company> get(int id) throws DAOException {
		try {
			return Optional.of(jdbcTemplate.queryForObject(REQUEST_GET_BY_ID, companyMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public List<Company> getAll() throws DAOException {
		try {
			return jdbcTemplate.query(REQUEST_GET_ALL, companyMapper);
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public List<Company> getAllOrderByName(boolean isDesc) throws DAOException {
		try {
			return jdbcTemplate.query(REQUEST_GET_ALL_ORDER_BY_NAME + (isDesc ? " DESC" : ""), companyMapper);
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public Optional<Company> get(String name) throws DAOException {
		try {
			return Optional.of(jdbcTemplate.queryForObject(REQUEST_GET_BY_NAME, companyMapper, name));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional
	public void delete(Company obj) throws DAOException {
		try {
			jdbcTemplate.update(REQUEST_DELETE_COMPUTER_BY_COMPANY_ID, obj.getId());
			jdbcTemplate.update(REQUEST_DELETE_COMPANY_BY_ID, obj.getId());
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}
}
