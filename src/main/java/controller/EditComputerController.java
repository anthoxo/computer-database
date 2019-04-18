package controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CompanyDTO;
import dto.ComputerDTO;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import mapper.CompanyMapper;
import mapper.ComputerMapper;
import model.Computer;
import service.CompanyService;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;
import validator.ComputerValidator;

@Controller
public class EditComputerController {

	CompanyService companyService;
	ComputerService computerService;
	NotificationService notificationService;
	CompanyMapper companyMapper;
	ComputerMapper computerMapper;
	MessageSource messageSource;

	public EditComputerController(CompanyService companyService, ComputerService computerService,
			NotificationService notificationService, CompanyMapper companyMapper, ComputerMapper computerMapper,
			MessageSource messageSource) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.notificationService = notificationService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
		this.messageSource = messageSource;
	}

	@PostMapping(Variable.URL_COMPUTER_EDIT)
	public String postEditComputer(@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO,
			BindingResult result, Locale locale) {

		String levelNotification = Variable.SUCCESS;
		String messageNotification = "computer.edit.notification.good";

		ComputerValidator computerValidator = new ComputerValidator();
		computerValidator.validate(computerDTO, result);

		if (result.hasErrors()) {
			levelNotification = Variable.DANGER;
			messageNotification = "computer.edit.notification.not_valid";
		} else {
			try {
				Computer computer = this.computerMapper.createBean(computerDTO);
				this.computerService.updateComputer(computer);
			} catch (ItemNotUpdatedException e) {
				levelNotification = Variable.DANGER;
				messageNotification = "computer.edit.notification.not_updated";
			} catch (ItemNotFoundException e) {
				levelNotification = Variable.DANGER;
				messageNotification = "computer.edit.notification.not_found";
			}
		}
		this.notificationService.generateNotification(levelNotification, this,
				this.messageSource.getMessage(messageNotification, null, locale));
		return "redirect:" + Variable.URL_COMPUTER;
	}

	@GetMapping(Variable.URL_COMPUTER_EDIT)
	public String getEditComputer(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			Locale locale) {
		int idComputer = 0;
		if (!"".equals(id)) {
			idComputer = Integer.valueOf(id);
		}
		try {
			Computer computer = this.computerService.getComputerById(idComputer);
			ComputerDTO cDTO = this.computerMapper.createDTO(computer);
			List<CompanyDTO> listCompanies = this.companyService.getAllCompanies().stream()
					.map(company -> this.companyMapper.createDTO(company)).collect(Collectors.toList());

			model.addAttribute("computerDTO", cDTO);
			model.addAttribute(Variable.COMPANY_LIST, listCompanies);

			return Variable.VIEW_COMPUTER_EDIT;
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification(Variable.DANGER, this,
					this.messageSource.getMessage("computer.edit.notification.not_found", null, locale));
			return "redirect:" + Variable.URL_COMPUTER;
		}
	}
}
