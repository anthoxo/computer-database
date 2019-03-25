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

	ComputerController computerController;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		computerController = (ComputerController) request.getSession().getAttribute("computer_controller");
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute("computer_controller", computerController);
		}

		String id = request.getParameter("id_delete");

		try {
			this.computerController.deleteComputer(Integer.valueOf(id));
			request.getSession().setAttribute("notification", "true");
			request.getSession().setAttribute("msgNotification", "This object has been correctly deleted !");
			request.getSession().setAttribute("lvlNotification", "success");
		} catch (ItemNotFoundException e) {
			request.getSession().setAttribute("notification", "true");
			request.getSession().setAttribute("msgNotification",
					"This object you want to delete is not found in database.");
			request.getSession().setAttribute("lvlNotification", "danger");
		} catch (ItemNotDeletedException e) {
			request.getSession().setAttribute("notification", "true");
			request.getSession().setAttribute("msgNotification", "This object hasn't been deleted.");
			request.getSession().setAttribute("lvlNotification", "danger");
		}

		response.sendRedirect(request.getContextPath() + "/computer");
	}

}
