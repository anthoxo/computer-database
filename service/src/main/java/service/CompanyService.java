package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import core.model.Company;
import core.util.Utils.OrderByOption;
import persistence.CompanyRepository;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;

@Service
public class CompanyService {

	CompanyRepository companyRepository;

	private Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	/**
	 * Retrieve all companies and return a list of company.
	 *
	 * @return List of companies.
	 * @throws ItemNotFoundException
	 */
	public List<Company> getAllCompanies() throws ItemNotFoundException {
		try {
			List<Company> result = new ArrayList<Company>();
			result = companyRepository.findAll();
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("companyService");
		}
	}

	public List<Company> getAllCompaniesOrderByName(OrderByOption option) throws ItemNotFoundException {
		try {
			List<Company> result = new ArrayList<Company>();
			boolean isDesc = option == OrderByOption.DESC ? true : false;
			if (isDesc) {
				result = companyRepository.findAllByOrderByNameDesc();
			} else {
				result = companyRepository.findAllByOrderByNameAsc();
			}
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("companyService");
		}
	}

	public Company getCompanyById(int id) throws ItemNotFoundException {
		try {
			Optional<Company> companyOpt = companyRepository.findById(id);
			if (companyOpt.isPresent()) {
				return companyOpt.get();
			} else {
				throw new ItemNotFoundException("getComputerById");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("companyService");
		}
	}

	public void deleteCompany(Company company) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			Optional<Company> companyOpt = companyRepository.findById(company.getId());
			if (companyOpt.isPresent()) {
				this.companyRepository.delete(companyOpt.get());
			} else {
				throw new ItemNotFoundException("deleteComputer");
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new ItemNotDeletedException("companyService");

		}
	}

}
