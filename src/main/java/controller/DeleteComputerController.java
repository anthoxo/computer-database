package controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import model.Computer;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;

@Controller
@RequestMapping(Variable.URL_COMPUTER + Variable.URL_DELETE)
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

	@PostMapping
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
