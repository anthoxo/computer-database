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

@WebServlet("/computer/search")
public class ComputerSearchServlet extends HttpServlet {

	private static final long serialVersionUID = -4860354040907739312L;

	public static final String GET_PARAMETER_ID = "id";

	public static final String COMPUTER_LIST = "computerList";
	public static final String NB_PAGES = "nbPages";
	public static final String ID_PAGE = "idPage";
	public static final String URL_PATH = "urlPath";
	public static final String IS_SEARCHING = "isSearching";
	public static final String COMPUTER_NUMBER = "computerNumber";

	public static final String URL_COMPUTER_SEARCH = "/computer/search";
	public static final String VIEW_COMPUTER = "/views/listComputers.jsp";

	public static final String COMPUTER_CONTROLLER = "computer_controller";

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

		computerController = (ComputerController) request.getSession().getAttribute(COMPUTER_CONTROLLER);
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute(COMPUTER_CONTROLLER, computerController);
		}

		String indexPage = request.getParameter(GET_PARAMETER_ID);
		int index;
		if (indexPage == null) {
			index = 0;
			computerPage = new Page<ComputerDTO>(computerController.getComputersByPattern(pattern));
		} else {
			index = Integer.valueOf(indexPage) - 1;
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		request.setAttribute(COMPUTER_LIST, computerPage.getEntitiesPage());
		request.setAttribute(NB_PAGES, computerPage.getNbPages());
		request.setAttribute(ID_PAGE, index + 1);
		request.setAttribute(URL_PATH, request.getContextPath() + URL_COMPUTER_SEARCH);
		request.setAttribute(IS_SEARCHING, "true");
		request.setAttribute(COMPUTER_NUMBER, computerPage.getLength());


		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(VIEW_COMPUTER);
		rd.forward(request, response);

	}

}
