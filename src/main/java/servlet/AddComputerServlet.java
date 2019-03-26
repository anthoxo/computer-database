package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.CompanyController;
import controller.ComputerController;
import dto.CompanyDTO;
import exception.ItemBadCreatedException;

@WebServlet("/computer/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -690181327611746612L;

	ComputerController computerController;
	CompanyController companyController;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String name, introduced, discontinued, companyId;

		name = request.getParameter("computerName");
		introduced = request.getParameter("introduced");
		discontinued = request.getParameter("discontinued");
		companyId = request.getParameter("companyId");

		try {
			this.computerController.createComputer(name, introduced, discontinued, Integer.valueOf(companyId));
			request.getSession().setAttribute("notification", "true");
			request.getSession().setAttribute("msgNotification", "This object has been correctly created !");
			request.getSession().setAttribute("lvlNotification", "success");

		} catch (ItemBadCreatedException e) {
			request.getSession().setAttribute("notification", "true");
			if (e.getMessage().equals("not-valid")) {
				request.getSession().setAttribute("msgNotification", "This object isn't valid.");
			} else {
				request.getSession().setAttribute("msgNotification", "This object hasn't been created.");
			}
			request.getSession().setAttribute("lvlNotification", "danger");
		}

		response.sendRedirect(request.getContextPath() + "/computer");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		computerController = (ComputerController) request.getSession().getAttribute("computer_controller");
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute("computer_controller", computerController);
		}

		companyController = (CompanyController) request.getSession().getAttribute("company_controller");
		if (companyController == null) {
			companyController = new CompanyController();
			request.getSession().setAttribute("company_controller", companyController);
		}

		List<CompanyDTO> listCompanies = this.companyController.getAllCompanies();
		request.setAttribute("listCompanies", listCompanies);

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/views/addComputer.jsp");
		rd.forward(request, response);
	}

}
