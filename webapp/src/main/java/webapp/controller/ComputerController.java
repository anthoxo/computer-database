package webapp.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import binding.dto.CompanyDTO;
import binding.dto.ComputerDTO;
import binding.mapper.CompanyMapper;
import binding.mapper.ComputerMapper;
import binding.validator.ComputerDTOValidator;
import core.model.Computer;
import core.model.Page;
import core.util.Utils.OrderByOption;
import core.util.Variable;
import persistence.exception.ItemBadCreatedException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import persistence.exception.ItemNotUpdatedException;
import service.CompanyService;
import service.ComputerService;
import service.NotificationService;

@Controller
@RequestMapping(Variable.URL_COMPUTER)
public class ComputerController {

	CompanyService companyService;
	ComputerService computerService;
	NotificationService notificationService;
	CompanyMapper companyMapper;
	ComputerMapper computerMapper;
	MessageSource messageSource;

	Page<ComputerDTO> computerPage;
	OrderByOption orderByOption;
	String orderColumn;
	String pattern = "";

	public ComputerController(CompanyService companyService, ComputerService computerService,
			NotificationService notificationService, CompanyMapper companyMapper, ComputerMapper computerMapper,
			MessageSource messageSource) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.notificationService = notificationService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
		this.messageSource = messageSource;
	}

	@GetMapping
	public String getComputerList(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_ORDER_BY, required = false, defaultValue = "") String orderBy) {
		this.pattern = "";
		int index = -1;
		try {
			index = Math.max(Integer.valueOf(id) - 1, 0);
		} catch (NumberFormatException e) {
			index = -1;
		}
		if (index == -1) {
			List<Computer> listComputers;
			try {
				if (!orderBy.equals("")) {
					switch (this.orderByOption) {
					case ASC:
						this.orderByOption = OrderByOption.DESC;
						break;
					default:
						this.orderByOption = OrderByOption.ASC;
						break;
					}
					if (!orderBy.equals(orderColumn)) {
						this.orderByOption = OrderByOption.ASC;
					}
					this.orderColumn = orderBy;
					listComputers = this.computerService.getAllComputersOrderBy(orderColumn, orderByOption);
				} else {
					listComputers = computerService.getAllComputers();
					orderByOption = OrderByOption.NULL;
				}
			} catch (ItemNotFoundException e) {
				listComputers = new ArrayList<Computer>();
			}
			this.computerPage = new Page<ComputerDTO>(listComputers.stream()
					.map(computer -> this.computerMapper.createDTO(computer)).collect(Collectors.toList()));
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		model.addAttribute(Variable.PAGE, computerPage);
		model.addAttribute(Variable.URL_PATH, Variable.URL_COMPUTER);
		model.addAttribute(Variable.IS_SEARCHING, false);

		if (this.notificationService.isNotifying()) {
			model.addAttribute(Variable.IS_NOTIFYING, true);
			model.addAttribute(Variable.NOTIFICATION, this.notificationService.getNotification());
			this.notificationService.clean();
		} else {
			model.addAttribute(Variable.IS_NOTIFYING, false);
		}
		return Variable.VIEW_COMPUTER;
	}

	@PostMapping(Variable.URL_SEARCH)
	public String postSearchComputers(
			@RequestParam(name = Variable.GET_PARAMETER_SEARCH, required = false, defaultValue = "") String pattern) {
		this.pattern = pattern;
		return "redirect:" + Variable.URL_COMPUTER + Variable.URL_SEARCH;
	}

	@GetMapping(Variable.URL_SEARCH)
	public String getSearchComputers(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_ORDER_BY, required = false, defaultValue = "") String orderBy) {
		int index = -1;
		try {
			index = Math.max(Integer.valueOf(id) - 1, 0);
		} catch (NumberFormatException e) {
			index = -1;
		}
		if (index == -1) {
			List<Computer> listComputers;
			try {
				if (!orderBy.equals("")) {
					switch (this.orderByOption) {
					case ASC:
						this.orderByOption = OrderByOption.DESC;
						break;
					default:
						this.orderByOption = OrderByOption.ASC;
						break;
					}
					if (!orderBy.equals(orderColumn)) {
						this.orderByOption = OrderByOption.ASC;
					}
					this.orderColumn = orderBy;
					listComputers = this.computerService.getComputersByPatternOrderBy(pattern, orderBy,
							this.orderByOption);
				} else {
					listComputers = this.computerService.getComputersByPattern(pattern);
					orderByOption = OrderByOption.NULL;
				}
			} catch (ItemNotFoundException e) {
				listComputers = new ArrayList<Computer>();
			}
			this.computerPage = new Page<ComputerDTO>(listComputers.stream()
					.map(computer -> this.computerMapper.createDTO(computer)).collect(Collectors.toList()));
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		model.addAttribute(Variable.PAGE, computerPage);
		model.addAttribute(Variable.URL_PATH, Variable.URL_COMPUTER + Variable.URL_SEARCH);
		model.addAttribute(Variable.IS_SEARCHING, true);

		if (this.notificationService.isNotifying()) {
			model.addAttribute(Variable.IS_NOTIFYING, true);
			model.addAttribute(Variable.NOTIFICATION, this.notificationService.getNotification());
			this.notificationService.clean();
		} else {
			model.addAttribute(Variable.IS_NOTIFYING, false);
		}
		return Variable.VIEW_COMPUTER;
	}

	@PostMapping(Variable.URL_ADD)
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
				Computer computer = this.computerMapper.createEntity(computerDTO);
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

	@GetMapping(Variable.URL_ADD)
	public String getAddComputer(Model model, Locale locale) {
		List<CompanyDTO> companyList;
		try {
			companyList = this.companyService.getAllCompanies().stream()
					.map(company -> this.companyMapper.createDTO(company)).collect(Collectors.toList());
			model.addAttribute(Variable.COMPANY_LIST, companyList);
			model.addAttribute("computerDTO", new ComputerDTO());
			return Variable.VIEW_COMPUTER_ADD;
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification(Variable.DANGER, this,
					this.messageSource.getMessage("computer.getCompanies.error", null, locale));
			return "redirect:" + Variable.URL_COMPUTER;
		}
	}

	@PostMapping(Variable.URL_EDIT)
	public String postEditComputer(@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO,
			BindingResult result, Locale locale) {

		String levelNotification = Variable.SUCCESS;
		String messageNotification = "computer.edit.notification.good";

		ComputerDTOValidator computerValidator = new ComputerDTOValidator();
		computerValidator.validate(computerDTO, result);

		if (result.hasErrors()) {
			levelNotification = Variable.DANGER;
			messageNotification = "computer.edit.notification.not_valid";
		} else {
			try {
				Computer computer = this.computerMapper.createEntity(computerDTO);
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

	@GetMapping("/{id}")
	public String getEditComputer(Model model, @PathVariable Integer id, Locale locale) {
		try {
			Computer computer = this.computerService.getComputerById(id);
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

	@PostMapping(Variable.URL_DELETE)
	public String deleteComputer(
			@RequestParam(name = Variable.GET_PARAMETER_ID_DELETE, required = false, defaultValue = "") String id,
			Locale locale) {

		String levelNotification = Variable.SUCCESS;
		String messageNotification = "computer.delete.notification.good";

		Computer computer = new Computer.Builder().withId(Integer.valueOf(id)).build();

		try {
			this.computerService.deleteComputer(computer);
		} catch (ItemNotFoundException e) {
			levelNotification = Variable.DANGER;
			messageNotification = "computer.delete.notification.not_found";
		} catch (ItemNotDeletedException e) {
			levelNotification = Variable.DANGER;
			messageNotification = "computer.delete.notification.not_deleted";
		}
		this.notificationService.generateNotification(levelNotification, this,
				this.messageSource.getMessage(messageNotification, null, locale));
		return "redirect:" + Variable.URL_COMPUTER;
	}
}
