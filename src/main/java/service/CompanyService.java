package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dao.CompanyDao;
import exception.DAOException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import model.Company;
import utils.Utils.OrderByOption;

@Service
public class CompanyService {

	CompanyDao companyDao;

	private Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private CompanyService(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	/**
	 * Retrieve all companies and return a list of company.
	 *
	 * @return List of companies.
	 */
	public List<Company> getAllCompanies() {
		List<Company> result = new ArrayList<Company>();
		try {
			result = companyDao.getAll();
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public List<Company> getAllCompaniesOrderByName(OrderByOption option) {
		List<Company> result = new ArrayList<Company>();
		boolean isDesc = option == OrderByOption.DESC ? true : false;
		try {
			result = companyDao.getAllOrderByName(isDesc);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public Company getCompanyById(int id) throws ItemNotFoundException {
		try {
			Optional<Company> companyOpt = this.companyDao.get(id);
			if (companyOpt.isPresent()) {
				return companyOpt.get();
			} else {
				throw new ItemNotFoundException("getComputerById");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException("dao");
		}
	}

	public void deleteCompany(Company company) throws ItemNotFoundException, ItemNotDeletedException {
		try {
			Optional<Company> companyOpt = companyDao.get(company.getId());
			if (companyOpt.isPresent()) {
				companyDao.delete(company);
			} else {
				throw new ItemNotFoundException("deleteComputer");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ItemNotDeletedException("dao");
		}
	}

}
