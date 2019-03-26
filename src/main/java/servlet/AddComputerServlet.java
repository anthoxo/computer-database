package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.CompanyController;
import controller.ComputerController;
import dto.CompanyDTO;
import exception.ItemBadCreatedException;

@WebServlet("/computer/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -690181327611746612L;

	public static final String GET_PARAMETER_NAME = "computerName";
	public static final String GET_PARAMETER_INTRODUCED = "introduced";
	public static final String GET_PARAMETER_DISCONTINUED = "discontinued";
	public static final String GET_PARAMETER_COMPANY_ID = "companyId";

	public static final String NOTIFICATION = "notification";
	public static final String MSG_NOTIFICATION = "msgNotification";
	public static final String LVL_NOTIFICATION = "lvlNotification";

	public static final String URL_COMPUTER = "/computer";
	public static final String VIEW_ADD = "/views/addComputer.jsp";

	public static final String COMPUTER_CONTROLLER = "computer_controller";
	public static final String COMPANY_CONTROLLER = "company_controller";
	public static final String COMPANY_LIST = "companyList";

	ComputerController computerController;
	CompanyController companyController;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String name, introduced, discontinued, companyId;

		name = request.getParameter(GET_PARAMETER_NAME);
		introduced = request.getParameter(GET_PARAMETER_INTRODUCED);
		discontinued = request.getParameter(GET_PARAMETER_DISCONTINUED);
		companyId = request.getParameter(GET_PARAMETER_COMPANY_ID);

		try {
			this.computerController.createComputer(name, introduced, discontinued, Integer.valueOf(companyId));
			request.getSession().setAttribute(NOTIFICATION, "true");
			request.getSession().setAttribute(MSG_NOTIFICATION, "This object has been correctly created !");
			request.getSession().setAttribute(LVL_NOTIFICATION, "success");

		} catch (ItemBadCreatedException e) {
			request.getSession().setAttribute(NOTIFICATION, "true");
			if (e.getMessage().equals("not-valid")) {
				request.getSession().setAttribute(MSG_NOTIFICATION, "This object isn't valid.");
			} else {
				request.getSession().setAttribute(MSG_NOTIFICATION, "This object hasn't been created.");
			}
			request.getSession().setAttribute(LVL_NOTIFICATION, "danger");
		}

		response.sendRedirect(request.getContextPath() + URL_COMPUTER);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		computerController = (ComputerController) request.getSession().getAttribute(COMPUTER_CONTROLLER);
		if (computerController == null) {
			computerController = new ComputerController();
			request.getSession().setAttribute(COMPUTER_CONTROLLER, computerController);
		}

		companyController = (CompanyController) request.getSession().getAttribute(COMPANY_CONTROLLER);
		if (companyController == null) {
			companyController = new CompanyController();
			request.getSession().setAttribute(COMPANY_CONTROLLER, companyController);
		}

		List<CompanyDTO> companyList = this.companyController.getAllCompanies();
		request.setAttribute(COMPANY_LIST, companyList);

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(VIEW_ADD);
		rd.forward(request, response);
	}

}
