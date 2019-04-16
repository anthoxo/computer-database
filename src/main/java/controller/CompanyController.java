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

	@GetMapping(Variable.URL_COMPANY)
	public String getCompanyList(Model model, @RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_ORDER_BY, required = false, defaultValue = "") String orderBy) {
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
		model.addAttribute(Variable.PAGE, companyPage);

		return Variable.VIEW_COMPANY;
	}
}
