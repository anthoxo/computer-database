package controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.ComputerDTO;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;

@Controller
public class DeleteComputerController {

	ComputerService computerService;
	NotificationService notificationService;
	MessageSource messageSource;

	public DeleteComputerController(ComputerService computerService, NotificationService notificationService,
			MessageSource messageSource) {
		this.computerService = computerService;
		this.notificationService = notificationService;
		this.messageSource = messageSource;
	}

	@PostMapping(Variable.URL_COMPUTER_DELETE)
	public String deleteComputer(
			@RequestParam(name = Variable.GET_PARAMETER_ID_DELETE, required = false, defaultValue = "") String id,
			Locale locale) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(Integer.valueOf(id));
		try {
			this.computerService.deleteComputer(computerDTO);
			this.notificationService.generateNotification("success", this, 0,
					this.messageSource.getMessage("computer.delete.notification.good", null, locale));
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, 0,
					this.messageSource.getMessage("computer.delete.notification.not_found", null, locale));
		} catch (ItemNotDeletedException e) {
			this.notificationService.generateNotification("danger", this, 0,
					this.messageSource.getMessage("computer.delete.notification.not_deleted", null, locale));
		}
		return "redirect:" + Variable.URL_COMPUTER;
	}

}
