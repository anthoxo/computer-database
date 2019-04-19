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

import dto.CompanyDTO;
import dto.ComputerDTO;
import exception.ItemBadCreatedException;
import mapper.CompanyMapper;
import mapper.ComputerMapper;
import model.Computer;
import service.CompanyService;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;
import validator.ComputerDTOValidator;

@Controller
public class AddComputerController {

	CompanyService companyService;
	ComputerService computerService;
	NotificationService notificationService;
	CompanyMapper companyMapper;
	ComputerMapper computerMapper;
	MessageSource messageSource;

	public AddComputerController(CompanyService companyService, ComputerService computerService,
			NotificationService notificationService, CompanyMapper companyMapper, ComputerMapper computerMapper,
			MessageSource messageSource) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.notificationService = notificationService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
		this.messageSource = messageSource;
	}

	@PostMapping(Variable.URL_COMPUTER_ADD)
	public String postAddComputer(@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO,
			BindingResult result, Locale locale) {

		String levelNotification = Variable.SUCCESS;
		String messageNotification = "computer.add.notification.good";

		ComputerDTOValidator computerValidator = new ComputerDTOValidator();
		computerValidator.validate(computerDTO, result);

		if (result.hasErrors()) {
			levelNotification = Variable.DANGER;
			messageNotification = "computer.add.notification.not_valid";
		} else {
			try {
				Computer computer = this.computerMapper.createBean(computerDTO);
				this.computerService.createComputer(computer);
			} catch (ItemBadCreatedException e) {
				levelNotification = Variable.DANGER;
				messageNotification = "computer.add.notification.not_created";
			}
		}
		this.notificationService.generateNotification(levelNotification, this,
				this.messageSource.getMessage(messageNotification, null, locale));
		return "redirect:" + Variable.URL_COMPUTER;
	}

	@GetMapping(Variable.URL_COMPUTER_ADD)
	public String getAddComputer(Model model) {
		List<CompanyDTO> companyList = this.companyService.getAllCompanies().stream()
				.map(company -> this.companyMapper.createDTO(company)).collect(Collectors.toList());
		model.addAttribute(Variable.COMPANY_LIST, companyList);
		model.addAttribute("computerDTO", new ComputerDTO());
		return Variable.VIEW_COMPUTER_ADD;
	}

}
