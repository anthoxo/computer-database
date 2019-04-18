package controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.ComputerDTO;
import mapper.ComputerMapper;
import model.Computer;
import model.Page;
import service.ComputerService;
import service.NotificationService;
import utils.Utils.OrderByOption;
import utils.Variable;

@Controller
public class ComputerController {

	ComputerService computerService;
	NotificationService notificationService;
	ComputerMapper computerMapper;

	Page<ComputerDTO> computerPage;
	OrderByOption orderByOption;
	String orderColumn;
	String pattern = "";

	public ComputerController(ComputerService computerService, NotificationService notificationService,
			ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.notificationService = notificationService;
		this.computerMapper = computerMapper;
	}

	@GetMapping(Variable.URL_COMPUTER)
	public String getComputerList(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_ORDER_BY, required = false, defaultValue = "") String orderBy) {
		this.pattern = "";
		int index = 0;
		if ("".equals(id)) {
			List<Computer> listComputers;
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
			this.computerPage = new Page<ComputerDTO>(listComputers.stream()
					.map(computer -> this.computerMapper.createDTO(computer)).collect(Collectors.toList()));
		} else {
			index = Integer.valueOf(id) - 1;
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		model.addAttribute(Variable.COMPUTER_LIST, computerPage.getEntitiesPage());
		model.addAttribute(Variable.PAGE, computerPage);
		model.addAttribute(Variable.URL_PATH, Variable.URL_COMPUTER);
		model.addAttribute(Variable.IS_SEARCHING, "false");

		if (this.notificationService.isNotifying()) {
			model.addAttribute(Variable.NOTIFICATION, true);
			model.addAttribute(Variable.MSG_NOTIFICATION, this.notificationService.getMessage());
			model.addAttribute(Variable.LVL_NOTIFICATION, this.notificationService.getLevel());
			this.notificationService.clean();
		} else {
			model.addAttribute(Variable.NOTIFICATION, false);
		}
		return Variable.VIEW_COMPUTER;
	}

	@PostMapping(Variable.URL_COMPUTER_SEARCH)
	public String postSearchComputers(
			@RequestParam(name = Variable.GET_PARAMETER_SEARCH, required = false, defaultValue = "") String pattern) {
		this.pattern = pattern;
		return "redirect:" + Variable.URL_COMPUTER_SEARCH;
	}

	@GetMapping(Variable.URL_COMPUTER_SEARCH)
	public String getSearchComputers(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_ORDER_BY, required = false, defaultValue = "") String orderBy) {
		int index = 0;
		if ("".equals(id)) {
			List<Computer> listComputers;
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
				listComputers = this.computerService.getComputersByPatternOrderBy(pattern, orderBy, this.orderByOption);
			} else {
				listComputers = this.computerService.getComputersByPattern(pattern);
				orderByOption = OrderByOption.NULL;
			}
			this.computerPage = new Page<ComputerDTO>(listComputers.stream()
					.map(computer -> this.computerMapper.createDTO(computer)).collect(Collectors.toList()));
		} else {
			index = Integer.valueOf(id) - 1;
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		model.addAttribute(Variable.COMPUTER_LIST, computerPage.getEntitiesPage());
		model.addAttribute(Variable.PAGE, computerPage);
		model.addAttribute(Variable.URL_PATH, Variable.URL_COMPUTER_SEARCH);
		model.addAttribute(Variable.IS_SEARCHING, "true");

		if (this.notificationService.isNotifying()) {
			model.addAttribute(Variable.NOTIFICATION, true);
			model.addAttribute(Variable.MSG_NOTIFICATION, this.notificationService.getMessage());
			model.addAttribute(Variable.LVL_NOTIFICATION, this.notificationService.getLevel());
			this.notificationService.clean();
		} else {
			model.addAttribute(Variable.NOTIFICATION, false);
		}
		return Variable.VIEW_COMPUTER;
	}

}
