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
import utils.Variable;

@WebServlet("/computer/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -690181327611746612L;

	ComputerController computerController;
	CompanyController companyController;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String name, introduced, discontinued, companyId;

		name = request.getParameter(Variable.GET_PARAMETER_NAME);
		introduced = request.getParameter(Variable.GET_PARAMETER_INTRODUCED);
		discontinued = request.getParameter(Variable.GET_PARAMETER_DISCONTINUED);
		companyId = request.getParameter(Variable.GET_PARAMETER_COMPANY_ID);

		try {
			this.computerController.createComputer(name, introduced, discontinued, Integer.valueOf(companyId));
			request.getSession().setAttribute(Variable.NOTIFICATION, "true");
			request.getSession().setAttribute(Variable.MSG_NOTIFICATION, "This object has been correctly created !");
			request.getSession().setAttribute(Variable.LVL_NOTIFICATION, "success");

		} catch (ItemBadCreatedException e) {
			request.getSession().setAttribute(Variable.NOTIFICATION, "true");
			if (e.getMessage().equals("not-valid")) {
				request.getSession().setAttribute(Variable.MSG_NOTIFICATION, "This object isn't valid.");
			} else {
				request.getSession().setAttribute(Variable.MSG_NOTIFICATION, "This object hasn't been created.");
			}
			request.getSession().setAttribute(Variable.LVL_NOTIFICATION, "danger");
		}

		response.sendRedirect(request.getContextPath() + Variable.URL_COMPUTER);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		computerController = (ComputerController) request.getSession().getAttribute(Variable.COMPUTER_CONTROLLER);
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute(Variable.COMPUTER_CONTROLLER, computerController);
		}

		companyController = (CompanyController) request.getSession().getAttribute(Variable.COMPANY_CONTROLLER);
		if (companyController == null) {
			companyController = new CompanyController();
			request.getSession().setAttribute(Variable.COMPANY_CONTROLLER, companyController);
		}

		List<CompanyDTO> companyList = this.companyController.getAllCompanies();
		request.setAttribute(Variable.COMPANY_LIST, companyList);

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_ADD);
		rd.forward(request, response);
	}

}
