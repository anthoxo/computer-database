package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import dto.CompanyDTO;
import model.Page;
import service.CompanyService;
import utils.Utils.OrderByOption;
import utils.Variable;

@WebServlet("/company")
public class CompanyListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	CompanyService companyService;

	Page<CompanyDTO> companyPage;
	OrderByOption orderByOption = OrderByOption.NULL;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String indexPage = request.getParameter(Variable.GET_PARAMETER_ID);
			int index;
			String orderBy;
			if (indexPage == null) {
				index = 0;
				orderBy = request.getParameter(Variable.GET_ORDER_BY);
				if ("name".equals(orderBy)) {
					switch (this.orderByOption) {
					case ASC:
						this.orderByOption = OrderByOption.DESC;
						break;
					default:
						this.orderByOption = OrderByOption.ASC;
						break;
					}
					companyPage = new Page<CompanyDTO>(companyService.getAllCompaniesOrderByName(orderByOption));
				} else {
					companyPage = new Page<CompanyDTO>(companyService.getAllCompanies());
				}
			} else {
				index = Integer.valueOf(indexPage) - 1;
			}

			companyPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

			request.setAttribute(Variable.COMPANY_LIST, companyPage.getEntitiesPage());
			request.setAttribute(Variable.NB_PAGES, companyPage.getNbPages());
			request.setAttribute(Variable.ID_PAGE, index + 1);

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_COMPANY);
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}
}
