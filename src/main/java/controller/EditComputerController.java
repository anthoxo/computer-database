package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/computer/edit")
	public String postEditComputer(
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_NAME, required = false, defaultValue = "") String name,
			@RequestParam(name = Variable.GET_PARAMETER_INTRODUCED, required = false, defaultValue = "") String introduced,
			@RequestParam(name = Variable.GET_PARAMETER_DISCONTINUED, required = false, defaultValue = "") String discontinued,
			@RequestParam(name = Variable.GET_PARAMETER_COMPANY_ID, required = false, defaultValue = "") String companyId) {

		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(Integer.valueOf(id));
		computerDTO.setName(name);
		computerDTO.setIntroducedDate(introduced);
		computerDTO.setDiscontinuedDate(discontinued);
		computerDTO.setCompanyId(Integer.valueOf(companyId));
		try {
			this.computerService.updateComputer(computerDTO);
			this.notificationService.generateNotification("success", this, 0,
					"This object has been correctly updated !");
		} catch (ItemNotUpdatedException e) {
			if (e.getMessage().equals("not-valid")) {
				this.notificationService.generateNotification("danger", this, 0,
						"This object isn't valid.");
			} else {
				this.notificationService.generateNotification("danger", this, 0,
						"This object hasn't been updated.");
			}
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, 0,
					"This object isn't in database.");
		}
		return "redirect:/computer";
	}

	@GetMapping("/computer/edit")
	public String getEditComputer(Model model,
			@RequestParam(name = "id", required = false, defaultValue = "") String id) {
		int idComputer = 0;
		if (!"".equals(id)) {
			idComputer = Integer.valueOf(id);
		}
		try {
			ComputerDTO cDTO = this.computerService.getComputerById(idComputer);
			model.addAttribute("computer", cDTO);
			List<CompanyDTO> listCompanies = this.companyService.getAllCompanies();

			model.addAttribute(Variable.COMPANY_LIST, listCompanies);

			return "editComputer";
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, 0, "This object isn't in database.");
			return "redirect:" + Variable.URL_COMPUTER;
		}
	}

}
