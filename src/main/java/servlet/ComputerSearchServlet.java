package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ComputerController;
import dto.ComputerDTO;
import model.Page;
import utils.Variable;

@WebServlet("/computer/search")
public class ComputerSearchServlet extends HttpServlet {

	private static final long serialVersionUID = -4860354040907739312L;

	ComputerController computerController;
	String pattern = "";
	Page<ComputerDTO> computerPage;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String search = request.getParameter("search");
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

		computerController = (ComputerController) request.getSession().getAttribute(Variable.COMPUTER_CONTROLLER);
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute(Variable.COMPUTER_CONTROLLER, computerController);
		}

		String indexPage = request.getParameter(Variable.GET_PARAMETER_ID);
		int index;
		String orderBy;
		if (indexPage == null) {
			index = 0;
			orderBy = request.getParameter(Variable.GET_ORDER_BY);
			if (orderBy != null) {
				computerController.refreshAttributeOrderBy(orderBy);
				computerPage = new Page<ComputerDTO>(computerController.getComputersByPatternOrderBy(pattern, orderBy));
			} else {
				computerPage = new Page<ComputerDTO>(computerController.getComputersByPattern(pattern));
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
