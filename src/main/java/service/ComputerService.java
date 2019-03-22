package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ComputerDAO;
import dto.ComputerDTO;
import exception.DAOException;
import exception.ItemBadCreatedException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import mapper.ComputerMapper;
import model.Computer;

public class ComputerService {
	ComputerDAO computerDAO;
	ComputerMapper computerMapper;

	private Logger logger = LoggerFactory.getLogger(ComputerService.class);

	/**
	 * Default constructor.
	 */
	public ComputerService() {
		computerDAO = ComputerDAO.getInstance();
		computerMapper = ComputerMapper.getInstance();
	}

	/**
	 * Create a new Computer in database using its DTO.
	 * @param computerDTO Computer DTO.
	 * @return true if this object has been created, else false.
	 * @throws ItemBadCreatedException
	 */
	public void createComputer(ComputerDTO computerDTO) throws ItemBadCreatedException {
		try {
			Computer c = computerMapper.createBean(computerDTO);
			computerDAO.create(c);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemBadCreatedException("createComputer");
		}
	}

	/**
	 * Retrieve a computer by its name.
	 *
	 * @param name Name of computer
	 * @return Optional computer DTO.
	 * @throws ItemNotFoundException
	 */
	public ComputerDTO getComputerByName(String name) throws ItemNotFoundException {
		try {
			Optional<Computer> c = computerDAO.get(name);
			if (c.isPresent()) {
				return computerMapper.createDTO(c.get());
			} else {
				throw new ItemNotFoundException("getComputerByName");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("getComputerByName");
		}
	}

	/**
	 * Retrieve a computer by its name.
	 *
	 * @param name Name of computer
	 * @return Optional computer DTO.
	 * @throws ItemNotFoundException
	 */
	public ComputerDTO getComputerById(int id) throws ItemNotFoundException {
		try {
			Optional<Computer> c = computerDAO.get(id);
			if (c.isPresent()) {
				return computerMapper.createDTO(c.get());
			} else {
				throw new ItemNotFoundException("getComputerById");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("getComputerById");
		}
	}


	/**
	 * Retrieve all computers and return a list of computers dto.
	 *
	 * @return List of computers DTO.
	 */
	public List<ComputerDTO> getAllComputers() {
		List<Computer> l;
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		try {
			l = computerDAO.getAll();
			l.forEach(computer -> result.add(computerMapper.createDTO(computer)));
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<ComputerDTO> getComputersByPattern(String pattern) {
		List<Computer> l;
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		try {
			l = computerDAO.getPattern(pattern);
			l.forEach(computer -> result.add(computerMapper.createDTO(computer)));
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public void updateComputer(ComputerDTO cDTO) throws ItemNotUpdatedException {

		try {
			Computer c = computerMapper.createBean(cDTO);
			computerDAO.update(c);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotUpdatedException("updateComputer");
		}

	}

	public void deleteComputer(ComputerDTO cDTO) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			Optional<Computer> computer = computerDAO.get(cDTO.getId());
			if (computer.isPresent()) {
				Computer c = computerMapper.createBean(cDTO);
				computerDAO.delete(c);
			} else {
				throw new ItemNotFoundException("deleteComputer");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotDeletedException("deleteComputer");
		}
	}
}
