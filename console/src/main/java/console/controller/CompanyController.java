package console.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import binding.dto.CompanyDTO;
import binding.mapper.CompanyMapper;
import core.model.Company;
import core.model.Page;
import core.util.Utils;
import core.util.Utils.ChoiceActionPage;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import service.CompanyService;

@Component
public class CompanyController {

	CompanyService companyService;
	CompanyMapper companyMapper;

	Page<CompanyDTO> companyPage;
	boolean isGoingBack;

	private CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
		this.isGoingBack = false;
	}

	/**
	 * Fetch company list and fill controller field.
	 */
	public void refreshCompanyPage() {
		List<CompanyDTO> listCompanies;
		try {
			listCompanies = companyService.getAllCompanies().stream()
					.map(company -> this.companyMapper.createDTO(company)).collect(Collectors.toList());
		} catch (ItemNotFoundException e) {
			listCompanies = new ArrayList<CompanyDTO>();
		}
		this.companyPage = new Page<CompanyDTO>(listCompanies);
	}

	public Page<CompanyDTO> getCompanyPage() {
		return this.companyPage;
	}

	public List<CompanyDTO> getAllCompanies() {
		try {
			return this.companyService.getAllCompanies().stream()
					.map(company -> this.companyMapper.createDTO(company)).collect(Collectors.toList());
		} catch (ItemNotFoundException e) {
			return new ArrayList<CompanyDTO>();
		}
	}

	public CompanyDTO getCompanyById(int id) throws ItemNotFoundException {
		Company company = this.companyService.getCompanyById(id);
		return this.companyMapper.createDTO(company);
	}

	public void deleteCompany(int id) throws ItemNotFoundException, ItemNotDeletedException {
		Company company = new Company.Builder().withId(id).build();
		this.companyService.deleteCompany(company);
	}

	/**
	 * Choose if we select the next or previous page.
	 *
	 * @param action Action processing.
	 * @return true if the choice corresponds to next/previous/back else false.
	 */
	public boolean selectAction(String action) {
		Utils.ChoiceActionPage choiceActionPage = Utils.stringToEnum(ChoiceActionPage.class, action);
		boolean badAction = true;
		switch (choiceActionPage) {
		case NEXT:
			this.companyPage.next();
			break;
		case PREVIOUS:
			this.companyPage.previous();
			break;
		case BACK:
			this.isGoingBack = true;
			break;
		default:
			badAction = false;
			break;
		}
		return badAction;
	}

	public boolean isGoingBack() {
		return isGoingBack;
	}
}
