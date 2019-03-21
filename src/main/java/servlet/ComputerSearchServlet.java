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

		computerController = (ComputerController) request.getSession().getAttribute("computer_controller");
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute("computer_controller", computerController);
		}

		String indexPage = request.getParameter("id");
		int index;
		if (indexPage == null) {
			index = 0;
			computerPage = new Page<ComputerDTO>(computerController.getComputersByPattern(pattern));
		} else {
			index = Integer.valueOf(indexPage) - 1;
		}

		computerPage.goTo(index * Page.NB_ITEMS_PER_PAGE);

		request.setAttribute("listComputers", computerPage.getEntitiesPage());
		request.setAttribute("nbPages", computerPage.getNbPages());
		request.setAttribute("idPage", index + 1);
		request.setAttribute("urlPath", "/computer/search");
		request.setAttribute("isSearching", "true");

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/views/listComputers.jsp");
		rd.forward(request, response);

	}


}
