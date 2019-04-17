package controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
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
	MessageSource messageSource;

	public AddComputerController(CompanyService companyService, ComputerService computerService,
			NotificationService notificationService, MessageSource messageSource) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.notificationService = notificationService;
		this.messageSource = messageSource;
	}

	@PostMapping(Variable.URL_COMPUTER_ADD)
	public String postAddComputer(@ModelAttribute("computerDTO") ComputerDTO computerDTO, Locale locale) {
		try {
			this.computerService.createComputer(computerDTO);
			this.notificationService.generateNotification("success", this, 0,
					this.messageSource.getMessage("computer.add.notification.good", null, locale));
		} catch (ItemBadCreatedException e) {
			if (e.getMessage().equals("not-valid")) {
				this.notificationService.generateNotification("danger", this, 0,
						this.messageSource.getMessage("computer.add.notification.not_valid", null, locale));
			} else {
				this.notificationService.generateNotification("danger", this, 0,
						this.messageSource.getMessage("computer.add.notification.not_created", null, locale));
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
