package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.CompanyDAO;
import dto.CompanyDTO;
import exception.DAOException;
import model.Company;

public class CompanyService {
	CompanyDAO companyDAO;

	private Logger logger = LoggerFactory.getLogger(CompanyService.class);

	/**
	 * Default constructor.
	 */
	public CompanyService() {
		companyDAO = CompanyDAO.getInstance();
	}

	/**
	 * Retrieve all companies and return a list of company dto.
	 * @return List of companies DTO.
	 */
	public List<CompanyDTO> getAllCompanies() {
		List<CompanyDTO> result = new ArrayList<CompanyDTO>();
		List<Company> l;
		try {
			l = companyDAO.getAll();
			l.forEach(company -> result.add(companyDAO.createDTO(Optional.of(company)).get()));
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public Optional<CompanyDTO> getCompanyByName(String name) {
		Optional<Company> c = Optional.ofNullable(null);
		try {
			c = companyDAO.get(name);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return companyDAO.createDTO(c);
	}

}
