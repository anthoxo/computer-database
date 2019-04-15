package controller;

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

	public DeleteComputerController(ComputerService computerService, NotificationService notificationService) {
		this.computerService = computerService;
		this.notificationService = notificationService;
	}

	@PostMapping("/computer/delete")
	public String deleteComputer(
			@RequestParam(name = Variable.GET_PARAMETER_ID_DELETE, required = false, defaultValue = "") String id) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(Integer.valueOf(id));
		try {
			this.computerService.deleteComputer(computerDTO);
			this.notificationService.generateNotification("success", this, 0,
					"This object has been correctly deleted !");
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, 0,
					"This object you want to delete is not found in database.");
		} catch (ItemNotDeletedException e) {
			this.notificationService.generateNotification("danger", this, 0,
					"This object hasn't been deleted.");
		}
		return "redirect:/computer";
	}

}
