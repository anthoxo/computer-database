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

@WebServlet("/computer/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -690181327611746612L;

	ComputerController computerController;
	CompanyController companyController;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name, introduced, discontinued, companyId;

		name = request.getParameter("computerName");
		introduced = request.getParameter("introduced");
		discontinued = request.getParameter("discontinued");
		companyId = request.getParameter("companyId");

		this.computerController.createComputer(name, introduced, discontinued, Integer.valueOf(companyId));

		response.sendRedirect("/index");
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
