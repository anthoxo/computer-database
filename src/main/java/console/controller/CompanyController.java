package console.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import dto.CompanyDTO;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import model.Page;
import service.CompanyService;
import utils.Utils;
import utils.Utils.ChoiceActionPage;

@Component
public class CompanyController {

	public CompanyService companyService;

	Page<CompanyDTO> companyPage;
	boolean isGoingBack;

	private CompanyController(CompanyService companyService) {
		this.companyService = companyService;
		this.isGoingBack = false;
	}

	/**
	 * Fetch company list and fill controller field.
	 */
	public void refreshCompanyPage() {
		List<CompanyDTO> listCompanies = companyService.getAllCompanies();
		this.companyPage = new Page<CompanyDTO>(listCompanies);
	}

	public Page<CompanyDTO> getCompanyPage() {
		return this.companyPage;
	}

	public List<CompanyDTO> getAllCompanies() {
		return this.companyService.getAllCompanies();
	}

	public CompanyDTO getCompanyById(int id) throws ItemNotFoundException {
		return this.companyService.getCompanyById(id);
	}

	public void deleteCompany(int id) throws ItemNotFoundException, ItemNotDeletedException  {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(id);
		this.companyService.deleteCompany(companyDTO);
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
