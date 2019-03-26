package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ComputerController;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;

@WebServlet("/computer/delete")
public class DeleteComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 6846348527025452256L;

	public static final String GET_PARAMETER_ID_DELETE = "id_delete";

	public static final String NOTIFICATION = "notification";
	public static final String MSG_NOTIFICATION = "msgNotification";
	public static final String LVL_NOTIFICATION = "lvlNotification";

	public static final String URL_COMPUTER = "/computer";

	public static final String COMPUTER_CONTROLLER = "computer_controller";

	ComputerController computerController;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		computerController = (ComputerController) request.getSession().getAttribute(COMPUTER_CONTROLLER);
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute(COMPUTER_CONTROLLER, computerController);
		}

		String id = request.getParameter(GET_PARAMETER_ID_DELETE);

		try {
			this.computerController.deleteComputer(Integer.valueOf(id));
			request.getSession().setAttribute(NOTIFICATION, "true");
			request.getSession().setAttribute(MSG_NOTIFICATION, "This object has been correctly deleted !");
			request.getSession().setAttribute(LVL_NOTIFICATION, "success");
		} catch (ItemNotFoundException e) {
			request.getSession().setAttribute(NOTIFICATION, "true");
			request.getSession().setAttribute(MSG_NOTIFICATION,
					"This object you want to delete is not found in database.");
			request.getSession().setAttribute(LVL_NOTIFICATION, "danger");
		} catch (ItemNotDeletedException e) {
			request.getSession().setAttribute(NOTIFICATION, "true");
			request.getSession().setAttribute(MSG_NOTIFICATION, "This object hasn't been deleted.");
			request.getSession().setAttribute(LVL_NOTIFICATION, "danger");
		}

		response.sendRedirect(request.getContextPath() + URL_COMPUTER);
	}

}
