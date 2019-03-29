package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.CompanyDAO;
import dto.CompanyDTO;
import exception.DAOException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import mapper.CompanyMapper;
import model.Company;
import utils.Utils.OrderByOption;

public class CompanyService {

	private static CompanyService instance = null;

	CompanyDAO companyDAO;
	CompanyMapper companyMapper;

	private Logger logger = LoggerFactory.getLogger(CompanyService.class);

	/**
	 * Default constructor.
	 */
	private CompanyService() {
		companyDAO = CompanyDAO.getInstance();
		companyMapper = CompanyMapper.getInstance();
	}

	public static CompanyService getInstance() {
		if (instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}

	/**
	 * Retrieve all companies and return a list of company dto.
	 *
	 * @return List of companies DTO.
	 */
	public List<CompanyDTO> getAllCompanies() {
		List<CompanyDTO> result = new ArrayList<CompanyDTO>();
		try {
			result = companyDAO.getAll().stream().map((Company c) -> companyMapper.createDTO(c))
					.collect(Collectors.toList());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<CompanyDTO> getAllCompaniesOrderByName(OrderByOption option) {
		List<CompanyDTO> result = new ArrayList<CompanyDTO>();
		boolean isDesc = option == OrderByOption.DESC ? true : false;
		try {
			result = companyDAO.getAllOrderByName(isDesc).stream().map((Company c) -> companyMapper.createDTO(c))
					.collect(Collectors.toList());
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public CompanyDTO getCompanyById(int id) throws ItemNotFoundException {
		try {
			Optional<Company> companyOpt = this.companyDAO.get(id);
			if (companyOpt.isPresent()) {
				return companyMapper.createDTO(companyOpt.get());
			} else {
				throw new ItemNotFoundException("getComputerById");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("dao");
		}
	}

	public void deleteCompany(CompanyDTO companyDTO) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			Optional<Company> companyOpt = companyDAO.get(companyDTO.getId());
			if (companyOpt.isPresent()) {
				Company company = companyMapper.createBean(companyDTO);
				companyDAO.delete(company);
			} else {
				throw new ItemNotFoundException("deleteComputer");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotDeletedException("dao");
		}
	}

}
