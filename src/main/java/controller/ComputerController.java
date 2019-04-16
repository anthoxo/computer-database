package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.ComputerDTO;
import model.Page;
import service.ComputerService;
import service.NotificationService;
import utils.Utils.OrderByOption;
import utils.Variable;

@Controller
public class ComputerController {

	ComputerService computerService;
	NotificationService notificationService;

	Page<ComputerDTO> computerPage;
	OrderByOption orderByOption;
	String orderColumn;
	String pattern = "";

	public ComputerController(ComputerService computerService, NotificationService notificationService) {
		this.computerService = computerService;
		this.notificationService = notificationService;
	}

	@GetMapping(Variable.URL_COMPUTER)
	public String getComputerList(Model model,
			@RequestParam(name = Variable.GET_PARAMETER_ID, required = false, defaultValue = "") String id,
			@RequestParam(name = Variable.GET_PARAMETER_ORDER_BY, required = false, defaultValue = "") String orderBy) {
		this.pattern = "";
		int index = 0;
		if ("".equals(id)) {
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
				this.computerPage = new Page<ComputerDTO>(
						computerService.getAllComputersOrderBy(orderColumn, orderByOption));
			} else {
				orderByOption = OrderByOption.NULL;
				this.computerPage = new Page<ComputerDTO>(computerService.getAllComputers());
			}
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
				this.computerPage = new Page<ComputerDTO>(
						this.computerService.getComputersByPatternOrderBy(pattern, orderBy, this.orderByOption));
			} else {
				orderByOption = OrderByOption.NULL;
				this.computerPage = new Page<ComputerDTO>(this.computerService.getComputersByPattern(pattern));
			}
		} else {
			index = Integer.valueOf(id) - 1;
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		model.addAttribute(Variable.COMPUTER_LIST, computerPage.getEntitiesPage());
		model.addAttribute(Variable.PAGE, computerPage);
		model.addAttribute(Variable.URL_PATH, Variable.URL_COMPUTER);
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
