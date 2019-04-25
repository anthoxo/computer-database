package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import core.model.Computer;
import core.util.Utils.OrderByOption;
import persistence.ComputerRepository;
import persistence.exception.ItemBadCreatedException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import persistence.exception.ItemNotUpdatedException;

@Service
public class ComputerService {

	ComputerRepository computerRepository;

	private Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService(ComputerRepository computerRepository) {
		this.computerRepository = computerRepository;
	}

	/**
	 * Create a new Computer in database.
	 *
	 * @param computer Computer.
	 * @return true if this object has been created, else false.
	 * @throws ItemBadCreatedException
	 * @throws ComputerException
	 */
	public void createComputer(Computer computer) throws ItemBadCreatedException {
		try {
			this.computerRepository.save(computer);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemBadCreatedException("computerRepository");
		}
	}

	/**
	 * Retrieve a computer by its name.
	 *
	 * @param name Name of computer
	 * @return Optional computer.
	 * @throws ItemNotFoundException
	 */
	public Computer getComputerByName(String name) throws ItemNotFoundException {
		try {
			List<Computer> listComputers = this.computerRepository.findByName(name);
			if (listComputers.size() > 0) {
				return listComputers.get(0);
			} else {
				throw new ItemNotFoundException("getComputerByName");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("computerRepository");
		}
	}

	/**
	 * Retrieve a computer by its name.
	 *
	 * @param name Name of computer
	 * @return Optional computer.
	 * @throws ItemNotFoundException
	 */
	public Computer getComputerById(int id) throws ItemNotFoundException {
		try {
			Optional<Computer> computerOpt = this.computerRepository.findById(id);
			if (computerOpt.isPresent()) {
				return computerOpt.get();
			} else {
				throw new ItemNotFoundException("getComputerById");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("dao");
		}
	}

	/**
	 * Retrieve all computers and return a list of computers.
	 *
	 * @return List of computers.
	 * @throws ItemNotFoundException
	 */
	public List<Computer> getAllComputers() throws ItemNotFoundException {
		try {
			List<Computer> result = new ArrayList<Computer>();
			result = this.computerRepository.findAll();
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("computerService");
		}
	}

	public List<Computer> getAllComputersOrderBy(String order, OrderByOption option) throws ItemNotFoundException {
		try {
			List<Computer> result = new ArrayList<Computer>();
			boolean isDesc = option == OrderByOption.DESC ? true : false;
			List<String> orders = Arrays.asList("name", "introduced", "discontinued", "company");
			if (orders.contains(order)) {
				if (order.equals("company")) {
					result = this.computerRepository.findAll(
							Sort.by(new Sort.Order(isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, "company.name")
									.nullsLast()));
				} else {
					result = this.computerRepository.findAll(Sort
							.by(new Sort.Order(isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, order).nullsLast()));
				}
			} else {
				result = this.getAllComputers();
			}
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("computerService");
		}
	}

	public List<Computer> getComputersByPattern(String pattern) throws ItemNotFoundException {
		try {
			List<Computer> result = new ArrayList<Computer>();
			result = this.computerRepository.findByNameContaining(pattern);
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("computerService");
		}
	}

	public List<Computer> getComputersByPatternOrderBy(String pattern, String order, OrderByOption option) throws ItemNotFoundException {
		try {
			boolean isDesc = option == OrderByOption.DESC ? true : false;
			List<Computer> result = new ArrayList<Computer>();
			List<String> orders = Arrays.asList("name", "introduced", "discontinued", "company");
			if (orders.contains(order)) {
				if ("company".equals(order)) {
					result = this.computerRepository.findByNameContaining(pattern,
							Sort.by(new Sort.Order(isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, "company.name").nullsLast()));
				} else {
					result = this.computerRepository.findByNameContaining(pattern,
							Sort.by(new Sort.Order(isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, order).nullsLast()));
				}
			} else {
				result = this.getComputersByPattern(pattern);
			}
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("computerService");
		}
	}

	public void updateComputer(Computer computer) throws ItemNotUpdatedException, ItemNotFoundException {
		try {
			Optional<Computer> computerOpt = this.computerRepository.findById(computer.getId());
			if (computerOpt.isPresent()) {
				this.computerRepository.save(computer);
			} else {
				throw new ItemNotUpdatedException("dao");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotUpdatedException("dao");
		}
	}

	public void deleteComputer(Computer computer) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			Optional<Computer> computerOpt = this.computerRepository.findById(computer.getId());
			if (computerOpt.isPresent()) {
				this.computerRepository.delete(computer);
			} else {
				throw new ItemNotFoundException("deleteComputer");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotDeletedException("dao");
		}
	}
}
