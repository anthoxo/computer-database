package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ComputerController;
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
		} catch (ItemNotFoundException e) {
			// print something to say NOT FOUND
		}

		response.sendRedirect("/index");
	}

}
