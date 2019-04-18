package dao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import exception.DAOException;
import exception.ItemNotFoundException;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;

@Repository
public class ComputerDao {

	static final String REQUEST_CREATE = "INSERT INTO computer VALUES (?,?,?,?,?)";
	static final String REQUEST_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	static final String REQUEST_DELETE = "DELETE FROM computer WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT id,name,introduced,discontinued,company_id FROM computer";
	static final String REQUEST_GET_ALL_ORDER_BY = "SELECT id,name,introduced,discontinued,company_id FROM computer ORDER BY ";
	static final String REQUEST_GET_ALL_ORDER_BY_COMPANY_NAME = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id ORDER BY company.name IS NULL ASC, company.name";
	static final String REQUEST_GET_BY_ID = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	static final String REQUEST_GET_BY_NAME = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name = ?";
	static final String REQUEST_GET_LIKE = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ?";
	static final String REQUEST_GET_LIKE_ORDER_BY = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ? ORDER BY ";
	static final String REQUEST_GET_LIKE_ORDER_BY_COMPANY_NAME = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ? ORDER BY company.name IS NULL ASC, company.name";

	static final String[] COMPUTER_COLUMN = { "id", "name", "introduced", "discontinued", "company_id" };

	JdbcTemplate jdbcTemplate;
	CompanyDao companyDao;
	ComputerMapper computerMapper;

	public ComputerDao(JdbcTemplate jdbcTemplate, CompanyDao companyDao, ComputerMapper computerMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.companyDao = companyDao;
		this.computerMapper = computerMapper;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void create(Computer obj) throws DAOException {
		try {
			Optional<Company> companyOpt = companyDao.get(obj.getCompanyId());
			if (companyOpt.isPresent()) {
				jdbcTemplate.update(REQUEST_CREATE, obj.getId(), obj.getName(), obj.getIntroduced(),
						obj.getDiscontinued(), obj.getCompanyId());
			} else {
				jdbcTemplate.update(REQUEST_CREATE, obj.getId(), obj.getName(), obj.getIntroduced(),
						obj.getDiscontinued(), null);
			}
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public Optional<Computer> get(int id) throws DAOException {
		try {
			return Optional.of(jdbcTemplate.queryForObject(REQUEST_GET_BY_ID, computerMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional
	public void update(Computer obj) throws DAOException, ItemNotFoundException {
		Optional<Computer> computerOpt = this.get(obj.getId());
		if (computerOpt.isPresent()) {
			Optional<Company> companyOpt = companyDao.get(obj.getCompanyId());
			try {
				if (companyOpt.isPresent()) {
					jdbcTemplate.update(REQUEST_UPDATE, obj.getName(), obj.getIntroduced(), obj.getDiscontinued(),
							obj.getCompanyId(), obj.getId());
				} else {
					jdbcTemplate.update(REQUEST_UPDATE, obj.getName(), obj.getIntroduced(), obj.getDiscontinued(), null,
							obj.getId());
				}
			} catch (DataAccessException e) {
				throw new DAOException(e);
			}
		} else {
			throw new ItemNotFoundException("update");
		}
	}

	@Transactional
	public void delete(Computer obj) throws DAOException {
		try {
			jdbcTemplate.update(REQUEST_DELETE, obj.getId());
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public List<Computer> getAll() throws DAOException {
		try {
			return jdbcTemplate.query(REQUEST_GET_ALL, computerMapper);
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public List<Computer> getAllOrderBy(String order, boolean isDesc) throws DAOException {
		try {
			String desc = isDesc ? " DESC" : "";
			if (Arrays.asList(COMPUTER_COLUMN).contains(order)) {
				String req = new StringBuilder().append(REQUEST_GET_ALL_ORDER_BY).append(order)
						.append(" IS NULL ASC, ").append(order).append(desc).toString();
				return jdbcTemplate.query(req, computerMapper);
			} else if (order.equals("companyName")) {
				return jdbcTemplate.query(REQUEST_GET_ALL_ORDER_BY_COMPANY_NAME + desc, computerMapper);
			} else {
				return getAll();
			}
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public Optional<Computer> get(String name) throws DAOException {
		try {
			return Optional.of(jdbcTemplate.queryForObject(REQUEST_GET_BY_NAME, computerMapper, name));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public List<Computer> getPattern(String pattern) throws DAOException {
		try {
			String patternRequest = new StringBuilder().append("%").append(pattern).append("%").toString();
			return jdbcTemplate.query(REQUEST_GET_LIKE, computerMapper, patternRequest);
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional(readOnly = true)
	public List<Computer> getPatternOrderBy(String pattern, String order, boolean isDesc) throws DAOException {
		String desc = isDesc ? " DESC" : "";
		String patternRequest = new StringBuilder().append("%").append(pattern).append("%").toString();
		if (Arrays.asList(COMPUTER_COLUMN).contains(order)) {
			StringBuilder req = new StringBuilder().append(REQUEST_GET_LIKE_ORDER_BY).append(order)
					.append(" IS NULL ASC, ").append(order).append(desc);
			return jdbcTemplate.query(req.toString(), computerMapper, patternRequest);
		} else if (order.equals("companyName")) {
			return jdbcTemplate.query(REQUEST_GET_LIKE_ORDER_BY_COMPANY_NAME + desc, computerMapper, patternRequest);
		} else {
			return getPattern(pattern);
		}
	}
}
