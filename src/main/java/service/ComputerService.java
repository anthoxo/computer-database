package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ComputerDAO;
import dto.ComputerDTO;
import exception.DAOException;
import model.Computer;

public class ComputerService {
	ComputerDAO computerDAO;

	private Logger logger = LoggerFactory.getLogger(ComputerService.class);

	/**
	 * Default constructor.
	 */
	public ComputerService() {
		computerDAO = ComputerDAO.getInstance();
	}

	/**
	 * Create a new Computer in database using its DTO.
	 * @param computerDTO Computer DTO.
	 * @return true if this object has been created, else false.
	 */
	public void createComputer(ComputerDTO computerDTO) {
		try {
			Optional<Computer> c = computerDAO.createBean(Optional.ofNullable(computerDTO));
			computerDAO.create(c.get());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Retrieve a computer by its name.
	 *
	 * @param name Name of computer
	 * @return Optional computer DTO.
	 */
	public Optional<ComputerDTO> getComputerByName(String name) {
		Optional<Computer> c = Optional.ofNullable(null);
		try {
			c = computerDAO.get(name);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return computerDAO.createDTO(c);
	}

	/**
	 * Retrieve a computer by its name.
	 *
	 * @param name Name of computer
	 * @return Optional computer DTO.
	 */
	public Optional<ComputerDTO> getComputerById(int id) {
		Optional<Computer> c = Optional.ofNullable(null);
		try {
			c = computerDAO.get(id);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return computerDAO.createDTO(c);
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
			l.forEach(computer -> result.add(computerDAO.createDTO(Optional.of(computer)).get()));

		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public void updateComputer(ComputerDTO cDTO) {

		try {
			Optional<Computer> c = computerDAO.createBean(Optional.ofNullable(cDTO));
			computerDAO.update(c.get());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}

	}

	public void deleteComputer(ComputerDTO cDTO) {

		try {
			Optional<Computer> c = computerDAO.createBean(Optional.ofNullable(cDTO));
			computerDAO.delete(c.get());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}

	}

}
