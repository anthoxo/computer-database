package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.CompanyController;
import model.Page;
import utils.Variable;

@WebServlet("/company")
public class CompanyListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	CompanyController companyController;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			companyController = (CompanyController) request.getSession().getAttribute(Variable.COMPANY_CONTROLLER);
			if (companyController == null) {
				companyController = new CompanyController();
				request.getSession().setAttribute(Variable.COMPANY_CONTROLLER, companyController);
			}

			String indexPage = request.getParameter(Variable.GET_PARAMETER_ID);
			int index;
			if (indexPage == null) {
				index = 0;
				companyController.refreshCompanyPage();
			} else {
				index = Integer.valueOf(indexPage) - 1;
			}

			this.companyController.getCompanyPage().goTo(index * Page.NB_ITEMS_PER_PAGE);

			request.setAttribute(Variable.COMPANY_LIST, this.companyController.getCompanyPage().getEntitiesPage());
			request.setAttribute(Variable.NB_PAGES, this.companyController.getCompanyPage().getNbPages());
			request.setAttribute(Variable.ID_PAGE, index + 1);

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_COMPANY);
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}

}
