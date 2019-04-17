package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import dao.ComputerDao;
import dto.ComputerDTO;
import exception.ComputerException;
import exception.DAOException;
import exception.ItemBadCreatedException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import mapper.ComputerMapper;
import model.Computer;
import utils.Utils.OrderByOption;
import validator.ComputerValidator;

@Service
public class ComputerService {

	ComputerDao computerDao;
	ComputerMapper computerMapper;

	ComputerValidator computerValidator;

	private Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService(ComputerDao computerDao, ComputerMapper computerMapper) {
		this.computerDao = computerDao;
		this.computerMapper = computerMapper;
		computerValidator = new ComputerValidator();
	}

	/**
	 * Create a new Computer in database using its DTO.
	 *
	 * @param computerDTO Computer DTO.
	 * @return true if this object has been created, else false.
	 * @throws ItemBadCreatedException
	 * @throws ComputerException
	 */
	public void createComputer(ComputerDTO computerDTO, BindingResult result) throws ItemBadCreatedException, ComputerException {

		ComputerValidator computerValidator = new ComputerValidator();
		computerValidator.validate(computerDTO, result);

		if (result.hasErrors()) {
			logger.error("This object is not valid.");
			throw new ComputerException("");
		}

		try {
			Computer computer = computerMapper.createBean(computerDTO);
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
	 * @return Optional computer DTO.
	 * @throws ItemNotFoundException
	 */
	public ComputerDTO getComputerByName(String name) throws ItemNotFoundException {
		try {
			Optional<Computer> computerOpt = computerDao.get(name);
			if (computerOpt.isPresent()) {
				return computerMapper.createDTO(computerOpt.get());
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
	 * @return Optional computer DTO.
	 * @throws ItemNotFoundException
	 */
	public ComputerDTO getComputerById(int id) throws ItemNotFoundException {
		try {
			Optional<Computer> computerOpt = computerDao.get(id);
			if (computerOpt.isPresent()) {
				return computerMapper.createDTO(computerOpt.get());
			} else {
				throw new ItemNotFoundException("getComputerById");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("dao");
		}
	}

	/**
	 * Retrieve all computers and return a list of computers dto.
	 *
	 * @return List of computers DTO.
	 */
	public List<ComputerDTO> getAllComputers() {
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		try {
			result = computerDao.getAll().stream().map((Computer computer) -> computerMapper.createDTO(computer))
					.collect(Collectors.toList());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<ComputerDTO> getAllComputersOrderBy(String order, OrderByOption option) {
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		boolean isDesc = option == OrderByOption.DESC ? true : false;
		try {
			result = computerDao.getAllOrderBy(order, isDesc).stream()
					.map((Computer computer) -> computerMapper.createDTO(computer)).collect(Collectors.toList());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<ComputerDTO> getComputersByPattern(String pattern) {
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		try {
			result = computerDao.getPattern(pattern).stream()
					.map((Computer computer) -> computerMapper.createDTO(computer)).collect(Collectors.toList());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<ComputerDTO> getComputersByPatternOrderBy(String pattern, String order, OrderByOption option) {
		boolean isDesc = option == OrderByOption.DESC ? true : false;
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		try {
			result = computerDao.getPatternOrderBy(pattern, order, isDesc).stream()
					.map((Computer computer) -> computerMapper.createDTO(computer)).collect(Collectors.toList());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public void updateComputer(ComputerDTO cDTO, BindingResult result) throws ItemNotUpdatedException, ItemNotFoundException, ComputerException {

		ComputerValidator computerValidator = new ComputerValidator();
		computerValidator.validate(cDTO, result);

		if (result.hasErrors()) {
			logger.error("This object is not valid.");
			throw new ComputerException("");
		}

		try {
			Computer computer = computerMapper.createBean(cDTO);
			computerDao.update(computer);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotUpdatedException("dao");
		}
	}

	public void deleteComputer(ComputerDTO cDTO) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			Optional<Computer> computerOpt = computerDao.get(cDTO.getId());
			if (computerOpt.isPresent()) {
				Computer computer = computerMapper.createBean(cDTO);
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
