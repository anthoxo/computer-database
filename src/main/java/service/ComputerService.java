package service;

import java.util.ArrayList;
import java.util.List;

import dao.ComputerDAO;
import dto.ComputerDTO;
import model.Computer;

public class ComputerService {
	ComputerDAO computerDAO;

	/**
	 * Default constructor.
	 */
	public ComputerService() {
		computerDAO = ComputerDAO.getInstance();
	}

//	/**
//	 * Create a new Computer in database using its DTO.
//	 * @param computerDTO Computer DTO.
//	 * @return true if this object has been created, else false.
//	 */
//	public boolean createComputer(ComputerDTO computerDTO) {
//		Computer c = computerDAO.createBean(computerDTO);
//		return computerDAO.create(c);
//	}

	/**
	 * Retrieve a computer by its name.
	 *
	 * @param name Name of computer
	 * @return The computer DTO (if it's found), else null.
	 */
	public ComputerDTO getComputerByName(String name) {
		Computer c = computerDAO.get(name);
		return computerDAO.createDTO(c);
	}

	/**
	 * Retrieve all computers and return a list of computers dto.
	 *
	 * @return List of computers DTO.
	 */
	public List<ComputerDTO> getAllComputers() {
		List<Computer> l = computerDAO.getAll();
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		l.forEach((Computer computer) -> result.add(computerDAO.createDTO(computer)));
		return result;
	}
}
