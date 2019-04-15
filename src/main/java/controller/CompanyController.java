package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CompanyDTO;
import model.Page;
import service.CompanyService;
import utils.Utils.OrderByOption;
import utils.Variable;

@Controller
public class CompanyController {

	CompanyService companyService;

	OrderByOption orderByOption = OrderByOption.NULL;
	Page<CompanyDTO> companyPage;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping("/company")
	public String getCompanyList(Model model, @RequestParam(name = "id", required = false, defaultValue = "") String id,
			@RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy) {
		int index = 0;
		if ("".equals(id)) {
			if ("name".equals(orderBy)) {
				switch (this.orderByOption) {
				case ASC:
					this.orderByOption = OrderByOption.DESC;
					break;
				default:
					this.orderByOption = OrderByOption.ASC;
					break;
				}
				companyPage = new Page<CompanyDTO>(companyService.getAllCompaniesOrderByName(orderByOption));
			} else {
				companyPage = new Page<CompanyDTO>(companyService.getAllCompanies());
			}
		} else {
			index = Integer.valueOf(id) - 1;
		}

		companyPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		model.addAttribute(Variable.COMPANY_LIST, companyPage.getEntitiesPage());
		model.addAttribute(Variable.NB_PAGES, companyPage.getNbPages());
		model.addAttribute(Variable.ID_PAGE, index + 1);
		return "listCompanies";
	}
}
