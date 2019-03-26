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

@WebServlet("/company")
public class CompanyListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	CompanyController companyController;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			companyController = (CompanyController) request.getSession().getAttribute("company_controller");
			if (companyController == null) {
				companyController = new CompanyController();
				request.getSession().setAttribute("company_controller", companyController);
			}

			String indexPage = request.getParameter("id");
			int index;
			if (indexPage == null) {
				index = 0;
				companyController.refreshCompanyPage();
			} else {
				index = Integer.valueOf(indexPage) - 1;
			}

			this.companyController.getCompanyPage().goTo(index * Page.NB_ITEMS_PER_PAGE);

			request.setAttribute("listCompanies", this.companyController.getCompanyPage().getEntitiesPage());
			request.setAttribute("nbPages", this.companyController.getCompanyPage().getNbPages());
			request.setAttribute("idPage", index + 1);

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/views/listCompanies.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}

}
