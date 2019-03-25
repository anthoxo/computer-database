package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ComputerController;
import model.Page;

@WebServlet("/computer")
public class ComputerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerController computerController;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			computerController = (ComputerController) request.getSession().getAttribute("computer_controller");
			if (computerController == null) {
				computerController = new ComputerController();
				request.getSession().setAttribute("computer_controller", computerController);
			}

			String indexPage = request.getParameter("id");
			int index;
			if (indexPage == null) {
				index = 0;
				computerController.refreshComputerPage();
			} else {
				index = Integer.valueOf(indexPage) - 1;
			}

			this.computerController.getComputerPage().goTo(index * Page.NB_ITEMS_PER_PAGE);

			request.setAttribute("listComputers", this.computerController.getComputerPage().getEntitiesPage());
			request.setAttribute("nbPages", this.computerController.getComputerPage().getNbPages());
			request.setAttribute("idPage", index + 1);
			request.setAttribute("isSearching", "false");

			String notification = (String) request.getSession().getAttribute("notification");

			if (notification == null || notification == "false") {
				request.setAttribute("notification", false);
			} else {
				String msgNotification = (String) request.getSession().getAttribute("msgNotification");
				String lvlNotification = (String) request.getSession().getAttribute("lvlNotification");
				request.setAttribute("notification", true);
				request.setAttribute("msgNotification", msgNotification);
				request.setAttribute("lvlNotification", lvlNotification);

				request.getSession().setAttribute("notification", "false");
				request.getSession().setAttribute("msgNotification", "");
				request.getSession().setAttribute("lvlNotification", "");
			}

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/views/listComputers.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}
}
