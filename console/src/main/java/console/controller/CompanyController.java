package console.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import binding.dto.CompanyDTO;
import binding.mapper.CompanyMapper;
import core.model.Page;
import core.model.User;
import core.util.Utils;
import core.util.Utils.ChoiceActionPage;

@Component
public class CompanyController {

	RestController restController;
	CompanyMapper companyMapper;

	Page<CompanyDTO> companyPage;
	boolean isGoingBack;
	User user;

	private CompanyController(RestController restController, CompanyMapper companyMapper) {
		this.restController = restController;
		this.companyMapper = companyMapper;
		this.isGoingBack = false;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Fetch company list and fill controller field.
	 */
	public void refreshCompanyPage() {
		List<CompanyDTO> listCompanies = getAllCompanies();
		this.companyPage = new Page<CompanyDTO>(listCompanies);
	}

	public Page<CompanyDTO> getCompanyPage() {
		return this.companyPage;
	}

	public List<CompanyDTO> getAllCompanies() {
		return this.restController.getAllCompanies(user);
	}

	public CompanyDTO getCompanyById(int id) {
		return this.restController.getCompany(user, id);
	}

	public boolean deleteCompany(int id) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(id);
		return this.restController.deleteCompany(user, companyDTO);
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
