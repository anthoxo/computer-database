package controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import dto.CompanyDTO;
import dto.ComputerDTO;
import exception.ComputerException;
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
	public String postAddComputer(@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO,
			BindingResult result, Locale locale) {
		String levelNotification = "success";
		String messageNotification = "computer.add.notification.good";
		try {
			this.computerService.createComputer(computerDTO, result);
		} catch (ItemBadCreatedException e) {
			levelNotification = "danger";
			messageNotification = "computer.add.notification.not_created";
		} catch (ComputerException e) {
			levelNotification = "danger";
			messageNotification = "computer.add.notification.not_valid";
		}
		this.notificationService.generateNotification(levelNotification, this, 0,
				this.messageSource.getMessage(messageNotification, null, locale));
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
