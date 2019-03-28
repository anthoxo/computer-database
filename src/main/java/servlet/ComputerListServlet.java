package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ComputerController;
import model.Page;

@WebServlet("/computer")
public class ComputerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String GET_PARAMETER_ID = "id";
	public static final String GET_ORDER_BY = "orderBy";

	public static final String COMPUTER_LIST = "computerList";
	public static final String NB_PAGES = "nbPages";
	public static final String ID_PAGE = "idPage";
	public static final String URL_PATH = "urlPath";
	public static final String IS_SEARCHING = "isSearching";
	public static final String COMPUTER_NUMBER = "computerNumber";

	public static final String NOTIFICATION = "notification";
	public static final String MSG_NOTIFICATION = "msgNotification";
	public static final String LVL_NOTIFICATION = "lvlNotification";

	public static final String URL_COMPUTER = "/computer";
	public static final String VIEW_COMPUTER = "/views/listComputers.jsp";

	public static final String COMPUTER_CONTROLLER = "computer_controller";

	ComputerController computerController;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			computerController = (ComputerController) request.getSession().getAttribute(COMPUTER_CONTROLLER);
			if (computerController == null) {
				computerController = new ComputerController();
				request.getSession().setAttribute(COMPUTER_CONTROLLER, computerController);
			}

			String indexPage = request.getParameter(GET_PARAMETER_ID);
			String orderBy;
			int index;
			if (indexPage == null) {
				index = 0;
				orderBy = request.getParameter(GET_ORDER_BY);
				if (orderBy != null) {
					computerController.refreshComputerPage(orderBy);
				} else {
					computerController.refreshComputerPage();
				}
			} else {
				index = Integer.valueOf(indexPage) - 1;
			}

			this.computerController.getComputerPage().goTo(index * Page.NB_ITEMS_PER_PAGE);

			request.setAttribute(COMPUTER_LIST, this.computerController.getComputerPage().getEntitiesPage());
			request.setAttribute(NB_PAGES, this.computerController.getComputerPage().getNbPages());
			request.setAttribute(ID_PAGE, index + 1);
			request.setAttribute(URL_PATH, request.getContextPath() + URL_COMPUTER);
			request.setAttribute(IS_SEARCHING, "false");
			request.setAttribute(COMPUTER_NUMBER, this.computerController.getComputerPage().getLength());

			String notification = (String) request.getSession().getAttribute(NOTIFICATION);

			if (notification == null || notification == "false") {
				request.setAttribute(NOTIFICATION, false);
			} else {
				String msgNotification = (String) request.getSession().getAttribute(MSG_NOTIFICATION);
				String lvlNotification = (String) request.getSession().getAttribute(LVL_NOTIFICATION);
				request.setAttribute(NOTIFICATION, true);
				request.setAttribute(MSG_NOTIFICATION, msgNotification);
				request.setAttribute(LVL_NOTIFICATION, lvlNotification);

				request.getSession().setAttribute(NOTIFICATION, "false");
				request.getSession().setAttribute(MSG_NOTIFICATION, "");
				request.getSession().setAttribute(LVL_NOTIFICATION, "");
			}

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(VIEW_COMPUTER);
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}
}
