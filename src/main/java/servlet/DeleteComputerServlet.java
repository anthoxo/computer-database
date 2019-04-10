package servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dto.ComputerDTO;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import main.MainConfig;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;

@WebServlet("/computer/delete")
public class DeleteComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 6846348527025452256L;

	ComputerService computerService;
	NotificationService notificationService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		AnnotationConfigApplicationContext applicationContext = MainConfig.getApplicationContext();
		this.computerService = applicationContext.getBean(ComputerService.class);
		this.notificationService = applicationContext.getBean(NotificationService.class);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ComputerDTO computerDTO = new ComputerDTO();
		String id = request.getParameter(Variable.GET_PARAMETER_ID_DELETE);
		computerDTO.setId(Integer.valueOf(id));
		try {
			this.computerService.deleteComputer(computerDTO);
			this.notificationService.generateNotification("success", this, serialVersionUID,
					"This object has been correctly deleted !");
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, serialVersionUID,
					"This object you want to delete is not found in database.");
		} catch (ItemNotDeletedException e) {
			this.notificationService.generateNotification("danger", this, serialVersionUID,
					"This object hasn't been deleted.");
		}
		response.sendRedirect(request.getContextPath() + Variable.URL_COMPUTER);
	}
}
