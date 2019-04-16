package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CompanyDTO;
import dto.ComputerDTO;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import service.CompanyService;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;

@Controller
public class EditComputerController {

	CompanyService companyService;
	ComputerService computerService;
	NotificationService notificationService;

	public EditComputerController(CompanyService companyService, ComputerService computerService,
			NotificationService notificationService) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.notificationService = notificationService;
	}

	@PostMapping(Variable.URL_COMPUTER_EDIT)
	public String postEditComputer(@ModelAttribute("computerDTO") ComputerDTO computerDTO) {
		try {
			this.computerService.updateComputer(computerDTO);
			this.notificationService.generateNotification("success", this, 0,
					"This object has been correctly updated !");
		} catch (ItemNotUpdatedException e) {
			if (e.getMessage().equals("not-valid")) {
				this.notificationService.generateNotification("danger", this, 0, "This object isn't valid.");
			} else {
				this.notificationService.generateNotification("danger", this, 0, "This object hasn't been updated.");
			}
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, 0, "This object isn't in database.");
		}
		return "redirect:" + Variable.URL_COMPUTER;
	}

	@GetMapping(Variable.URL_COMPUTER_EDIT)
	public String getEditComputer(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id) {
		int idComputer = 0;
		if (!"".equals(id)) {
			idComputer = Integer.valueOf(id);
		}
		try {
			ComputerDTO cDTO = this.computerService.getComputerById(idComputer);
			List<CompanyDTO> listCompanies = this.companyService.getAllCompanies();

			model.addAttribute("computerDTO", cDTO);
			model.addAttribute(Variable.COMPANY_LIST, listCompanies);

			return Variable.VIEW_COMPUTER_EDIT;
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, 0, "This object isn't in database.");
			return "redirect:" + Variable.URL_COMPUTER;
		}
	}
}
