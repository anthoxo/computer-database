package webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import binding.dto.CompanyDTO;
import binding.mapper.CompanyMapper;
import core.model.Company;
import core.model.Page;
import core.util.Utils.OrderByOption;
import core.util.Variable;
import persistence.exception.ItemNotFoundException;
import service.CompanyService;

@Controller
@RequestMapping(Variable.URL_COMPANY)
public class CompanyController {

	CompanyService companyService;
	CompanyMapper companyMapper;

	OrderByOption orderByOption = OrderByOption.NULL;
	Page<CompanyDTO> companyPage;

	public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}

	@GetMapping
	@Secured("ROLE_USER")
	public String getCompanyList(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_ORDER_BY, required = false, defaultValue = "") String orderBy) {
		int index = -1;
		try {
			index = Math.max(Integer.valueOf(id) - 1, 0);
		} catch (NumberFormatException e) {
			index = -1;
		}
		if (index == -1) {
			List<Company> listCompanies;
			try {
				if ("name".equals(orderBy)) {
					switch (this.orderByOption) {
					case ASC:
						this.orderByOption = OrderByOption.DESC;
						break;
					default:
						this.orderByOption = OrderByOption.ASC;
						break;
					}
					listCompanies = this.companyService.getAllCompaniesOrderByName(orderByOption);
				} else {
					listCompanies = this.companyService.getAllCompanies();
				}
			} catch (ItemNotFoundException e) {
				listCompanies = new ArrayList<Company>();
			}
			this.companyPage = new Page<CompanyDTO>(listCompanies.stream()
					.map(company -> this.companyMapper.createDTO(company)).collect(Collectors.toList()));
		}
		companyPage.goTo(index * Page.NB_ITEMS_PER_PAGE);
		model.addAttribute(Variable.PAGE, companyPage);
		return Variable.VIEW_COMPANY;
	}
}
