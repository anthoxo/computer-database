package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CompanyDTO;
import dto.ComputerDTO;
import exception.ItemBadCreatedException;
import service.CompanyService;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;

@Controller
public class AddComputerController {

	CompanyService companyService;
	ComputerService computerService;
	NotificationService notificationService;

	public AddComputerController(CompanyService companyService, ComputerService computerService,
			NotificationService notificationService) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.notificationService = notificationService;
	}

	@PostMapping("/computer/add")
	public String postAddComputer(
			@RequestParam(name = Variable.GET_PARAMETER_NAME, required = false, defaultValue = "") String name,
			@RequestParam(name = Variable.GET_PARAMETER_INTRODUCED, required = false, defaultValue = "") String introduced,
			@RequestParam(name = Variable.GET_PARAMETER_DISCONTINUED, required = false, defaultValue = "") String discontinued,
			@RequestParam(name = Variable.GET_PARAMETER_COMPANY_ID, required = false, defaultValue = "") String companyId) {

		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(name);
		computerDTO.setIntroducedDate(introduced);
		computerDTO.setDiscontinuedDate(discontinued);
		int companyIdInt = 0;
		try {
			companyIdInt = Integer.parseInt(companyId);
		} catch (NumberFormatException e) {
			companyIdInt = 0;
		}
		computerDTO.setCompanyId(companyIdInt);
		try {
			this.computerService.createComputer(computerDTO);
			this.notificationService.generateNotification("success", this, 0,
					"This object has been correctly created !");
		} catch (ItemBadCreatedException e) {
			if (e.getMessage().equals("not-valid")) {
				this.notificationService.generateNotification("danger", this, 0,
						"This object isn't valid.");
			} else {
				this.notificationService.generateNotification("danger", this, 0,
						"This object hasn't been created.");
			}
		}
		return "redirect:/computer";
	}

	@GetMapping("/computer/add")
	public String getAddComputer(Model model) {
		List<CompanyDTO> companyList = this.companyService.getAllCompanies();
		model.addAttribute(Variable.COMPANY_LIST, companyList);
		return "addComputer";
	}

}
