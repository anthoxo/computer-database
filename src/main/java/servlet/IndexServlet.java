package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "/views/index.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(VIEW_INDEX);
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}

}
