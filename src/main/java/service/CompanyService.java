package service;

import java.util.ArrayList;
import java.util.List;

import dao.CompanyDAO;
import dto.CompanyDTO;
import model.Company;

public class CompanyService {
	CompanyDAO companyDAO;

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
		List<Company> l = companyDAO.getAll();
		List<CompanyDTO> result = new ArrayList<CompanyDTO>();
		l.forEach((Company company) -> result.add(companyDAO.createDTO(company)));
		return result;
	}
}
