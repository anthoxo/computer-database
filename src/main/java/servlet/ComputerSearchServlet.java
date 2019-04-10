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

import dto.ComputerDTO;
import model.Page;
import service.ComputerService;
import utils.Utils.OrderByOption;
import utils.Variable;

@WebServlet("/computer/search")
public class ComputerSearchServlet extends HttpServlet {

	private static final long serialVersionUID = -4860354040907739312L;

	@Autowired
	ComputerService computerService;

	String pattern = "";
	Page<ComputerDTO> computerPage;
	String orderBy;
	OrderByOption orderByOption = OrderByOption.NULL;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String search = request.getParameter(Variable.GET_PARAMETER_SEARCH);
		if (search != null) {
			pattern = search;
		} else {
			pattern = "";
		}
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String indexPage = request.getParameter(Variable.GET_PARAMETER_ID);
		int index;
		String orderByTmp;
		if (indexPage == null) {
			index = 0;
			orderByTmp = request.getParameter(Variable.GET_ORDER_BY);
			if (orderByTmp != null) {
				switch (this.orderByOption) {
				case ASC:
					this.orderByOption = OrderByOption.DESC;
					break;
				default:
					this.orderByOption = OrderByOption.ASC;
					break;
				}
				if (!orderByTmp.equals(this.orderBy)) {
					this.orderByOption = OrderByOption.ASC;
				}
				this.orderBy = orderByTmp;
				computerPage = new Page<ComputerDTO>(
						this.computerService.getComputersByPatternOrderBy(pattern, this.orderBy, this.orderByOption));
			} else {
				computerPage = new Page<ComputerDTO>(this.computerService.getComputersByPattern(pattern));
			}
		} else {
			index = Integer.valueOf(indexPage) - 1;
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		request.setAttribute(Variable.COMPUTER_LIST, computerPage.getEntitiesPage());
		request.setAttribute(Variable.NB_PAGES, computerPage.getNbPages());
		request.setAttribute(Variable.ID_PAGE, index + 1);
		request.setAttribute(Variable.URL_PATH, request.getContextPath() + Variable.URL_COMPUTER_SEARCH);
		request.setAttribute(Variable.IS_SEARCHING, "true");
		request.setAttribute(Variable.COMPUTER_NUMBER, computerPage.getLength());

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_COMPUTER);
		rd.forward(request, response);
	}
}
