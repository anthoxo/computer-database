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
import exception.ItemNotFoundException;
import mapper.CompanyMapper;
import model.Company;

public class CompanyService {
	CompanyDAO companyDAO;
	CompanyMapper companyMapper;

	private Logger logger = LoggerFactory.getLogger(CompanyService.class);

	/**
	 * Default constructor.
	 */
	public CompanyService() {
		companyDAO = CompanyDAO.getInstance();
		companyMapper = CompanyMapper.getInstance();
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

	public CompanyDTO getCompanyByName(String name) throws ItemNotFoundException {
		try {
			Optional<Company> company = companyDAO.get(name);
			if (company.isPresent()) {
				return companyMapper.createDTO(company.get());
			} else {
				throw new ItemNotFoundException("getCompanyByName");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("getCompanyByName");
		}
	}

}
