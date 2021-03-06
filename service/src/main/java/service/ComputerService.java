package service;

import java.util.Arrays;
import java.util.List;

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

	private static final String COMPUTER_REPOSITORY = "computerRepository";
	private static final List<String> ORDERS = Arrays.asList("name", "introduced", "discontinued", "company");


	ComputerService(ComputerRepository computerRepository) {
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
			throw new ItemBadCreatedException(COMPUTER_REPOSITORY);
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
			return this.computerRepository.findByName(name)
					.orElseThrow(() -> new ItemNotFoundException("getComputerByName"));
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(COMPUTER_REPOSITORY);
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
			return this.computerRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("getComputerById"));
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(COMPUTER_REPOSITORY);
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
			return this.computerRepository.findAll();
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(COMPUTER_REPOSITORY);
		}
	}

	public List<Computer> getAllComputersOrderBy(String order, OrderByOption option) throws ItemNotFoundException {
		try {
			List<Computer> result;
			Sort.Direction direction = option == OrderByOption.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
			if (ORDERS.contains(order)) {
				if (order.equals(ORDERS.get(3))) {
					//company
					result = this.computerRepository.findAll(Sort.by(new Sort.Order(direction, "company.name")));
				} else {
					result = this.computerRepository.findAll(Sort.by(new Sort.Order(direction, order)));
				}
			} else {
				result = this.getAllComputers();
			}
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(COMPUTER_REPOSITORY);
		}
	}

	public List<Computer> getComputersByPattern(String pattern) throws ItemNotFoundException {
		try {
			return this.computerRepository.findByNameContaining(pattern);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(COMPUTER_REPOSITORY);
		}
	}

	public List<Computer> getComputersByPatternOrderBy(String pattern, String order, OrderByOption option)
			throws ItemNotFoundException {
		try {
			List<Computer> result;
			Sort.Direction direction = option == OrderByOption.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
			if (ORDERS.contains(order)) {
				if (order.equals(ORDERS.get(3))) {
					// company
					result = this.computerRepository.findByNameContaining(pattern,
							Sort.by(new Sort.Order(direction, "company.name")));
				} else {
					result = this.computerRepository.findByNameContaining(pattern,
							Sort.by(new Sort.Order(direction, order)));
				}
			} else {
				result = this.getComputersByPattern(pattern);
			}
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(COMPUTER_REPOSITORY);
		}
	}

	public void updateComputer(Computer computer) throws ItemNotUpdatedException, ItemNotFoundException {
		try {
			if (this.computerRepository.findById(computer.getId()).isPresent()) {
				this.computerRepository.save(computer);
			} else {
				throw new ItemNotUpdatedException("updateComputer");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotUpdatedException(COMPUTER_REPOSITORY);
		}
	}

	public void deleteComputer(Computer computer) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			if (this.computerRepository.findById(computer.getId()).isPresent()) {
				this.computerRepository.delete(computer);
			} else {
				throw new ItemNotFoundException("deleteComputer");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotDeletedException(COMPUTER_REPOSITORY);
		}
	}
}
