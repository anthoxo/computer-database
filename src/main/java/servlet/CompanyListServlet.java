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

	public static final String GET_PARAMETER_ID = "id";

	public static final String COMPANY_LIST = "companyList";
	public static final String NB_PAGES = "nbPages";
	public static final String ID_PAGE = "idPage";

	public static final String VIEW_COMPANY = "/views/listCompanies.jsp";

	public static final String COMPANY_CONTROLLER = "company_controller";

	CompanyController companyController;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			companyController = (CompanyController) request.getSession().getAttribute(COMPANY_CONTROLLER);
			if (companyController == null) {
				companyController = new CompanyController();
				request.getSession().setAttribute(COMPANY_CONTROLLER, companyController);
			}

			String indexPage = request.getParameter(GET_PARAMETER_ID);
			int index;
			if (indexPage == null) {
				index = 0;
				companyController.refreshCompanyPage();
			} else {
				index = Integer.valueOf(indexPage) - 1;
			}

			this.companyController.getCompanyPage().goTo(index * Page.NB_ITEMS_PER_PAGE);

			request.setAttribute(COMPANY_LIST, this.companyController.getCompanyPage().getEntitiesPage());
			request.setAttribute(NB_PAGES, this.companyController.getCompanyPage().getNbPages());
			request.setAttribute(ID_PAGE, index + 1);

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(VIEW_COMPANY);
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}

}
