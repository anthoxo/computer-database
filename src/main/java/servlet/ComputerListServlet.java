package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDTO;
import model.Page;
import service.ComputerService;
import utils.Utils.OrderByOption;
import utils.Variable;

@WebServlet("/computer")
public class ComputerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerService computerService = ComputerService.getInstance();
	Page<ComputerDTO> computerPage;
	OrderByOption orderByOption;
	String orderBy;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String indexPage = request.getParameter(Variable.GET_PARAMETER_ID);
			String orderByTmp;
			int index;
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
					if (!orderByTmp.equals(orderBy)) {
						this.orderByOption = OrderByOption.ASC;
					}
					this.orderBy = orderByTmp;
					this.computerPage = new Page<ComputerDTO>(
							computerService.getAllComputersOrderBy(orderBy, orderByOption));
				} else {
					orderByOption = OrderByOption.NULL;
					this.computerPage = new Page<ComputerDTO>(computerService.getAllComputers());
				}
			} else {
				index = Integer.valueOf(indexPage) - 1;
			}

			computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

			request.setAttribute(Variable.COMPUTER_LIST, computerPage.getEntitiesPage());
			request.setAttribute(Variable.NB_PAGES, computerPage.getNbPages());
			request.setAttribute(Variable.ID_PAGE, index + 1);
			request.setAttribute(Variable.URL_PATH, request.getContextPath() + Variable.URL_COMPUTER);
			request.setAttribute(Variable.IS_SEARCHING, "false");
			request.setAttribute(Variable.COMPUTER_NUMBER, computerPage.getLength());

			String notification = (String) request.getSession().getAttribute(Variable.NOTIFICATION);

			if (notification == null || notification == "false") {
				request.setAttribute(Variable.NOTIFICATION, false);
			} else {
				String msgNotification = (String) request.getSession().getAttribute(Variable.MSG_NOTIFICATION);
				String lvlNotification = (String) request.getSession().getAttribute(Variable.LVL_NOTIFICATION);
				request.setAttribute(Variable.NOTIFICATION, true);
				request.setAttribute(Variable.MSG_NOTIFICATION, msgNotification);
				request.setAttribute(Variable.LVL_NOTIFICATION, lvlNotification);

				request.getSession().setAttribute(Variable.NOTIFICATION, "false");
				request.getSession().setAttribute(Variable.MSG_NOTIFICATION, "");
				request.getSession().setAttribute(Variable.LVL_NOTIFICATION, "");
			}

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_COMPUTER);
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}
}
