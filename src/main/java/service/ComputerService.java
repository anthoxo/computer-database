package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dao.ComputerDao;
import exception.DAOException;
import exception.ItemBadCreatedException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import model.Computer;
import utils.Utils.OrderByOption;
import validator.ComputerDTOValidator;

@Service
public class ComputerService {

	ComputerDao computerDao;

	ComputerDTOValidator computerValidator;

	private Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService(ComputerDao computerDao) {
		this.computerDao = computerDao;
		computerValidator = new ComputerDTOValidator();
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
			computerDao.create(computer);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemBadCreatedException("dao");
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
			Optional<Computer> computerOpt = computerDao.get(name);
			if (computerOpt.isPresent()) {
				return computerOpt.get();
			} else {
				throw new ItemNotFoundException("getComputerByName");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("dao");
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
			Optional<Computer> computerOpt = computerDao.get(id);
			if (computerOpt.isPresent()) {
				return computerOpt.get();
			} else {
				throw new ItemNotFoundException("getComputerById");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("dao");
		}
	}

	/**
	 * Retrieve all computers and return a list of computers.
	 *
	 * @return List of computers.
	 */
	public List<Computer> getAllComputers() {
		List<Computer> result = new ArrayList<Computer>();
		try {
			result = computerDao.getAll();
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<Computer> getAllComputersOrderBy(String order, OrderByOption option) {
		List<Computer> result = new ArrayList<Computer>();
		boolean isDesc = option == OrderByOption.DESC ? true : false;
		try {
			result = computerDao.getAllOrderBy(order, isDesc);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<Computer> getComputersByPattern(String pattern) {
		List<Computer> result = new ArrayList<Computer>();
		try {
			result = computerDao.getPattern(pattern);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<Computer> getComputersByPatternOrderBy(String pattern, String order, OrderByOption option) {
		boolean isDesc = option == OrderByOption.DESC ? true : false;
		List<Computer> result = new ArrayList<Computer>();
		try {
			result = computerDao.getPatternOrderBy(pattern, order, isDesc);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public void updateComputer(Computer computer) throws ItemNotUpdatedException, ItemNotFoundException {
		try {
			computerDao.update(computer);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotUpdatedException("dao");
		}
	}

	public void deleteComputer(Computer computer) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			Optional<Computer> computerOpt = computerDao.get(computer.getId());
			if (computerOpt.isPresent()) {
				computerDao.delete(computer);
			} else {
				throw new ItemNotFoundException("deleteComputer");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotDeletedException("dao");
		}
	}
}
