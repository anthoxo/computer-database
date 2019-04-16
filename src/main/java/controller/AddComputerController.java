package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

	@PostMapping(Variable.URL_COMPUTER_ADD)
	public String postAddComputer(@ModelAttribute("computerDTO") ComputerDTO computerDTO) {
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
		return "redirect:" + Variable.URL_COMPUTER;
	}

	@GetMapping(Variable.URL_COMPUTER_ADD)
	public String getAddComputer(Model model) {
		List<CompanyDTO> companyList = this.companyService.getAllCompanies();
		model.addAttribute(Variable.COMPANY_LIST, companyList);
		model.addAttribute("computerDTO", new ComputerDTO());
		return Variable.VIEW_COMPUTER_ADD;
	}

}
