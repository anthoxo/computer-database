package servlet;

import java.io.IOException;
import java.util.List;

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
import dto.ComputerDTO;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import service.CompanyService;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;

@WebServlet("/computer/edit")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 8206412986998744720L;

	@Autowired
	ComputerService computerService;

	@Autowired
	CompanyService companyService;

	@Autowired
	NotificationService notificationService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String id = request.getParameter(Variable.GET_PARAMETER_ID);
		String companyId = request.getParameter(Variable.GET_PARAMETER_COMPANY_ID);
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(Integer.valueOf(id));
		computerDTO.setName(request.getParameter(Variable.GET_PARAMETER_NAME));
		computerDTO.setIntroducedDate(request.getParameter(Variable.GET_PARAMETER_INTRODUCED));
		computerDTO.setDiscontinuedDate(request.getParameter(Variable.GET_PARAMETER_DISCONTINUED));
		computerDTO.setCompanyId(Integer.valueOf(companyId));
		try {
			this.computerService.updateComputer(computerDTO);
			this.notificationService.generateNotification("success", this, serialVersionUID,
					"This object has been correctly updated !");
		} catch (ItemNotUpdatedException e) {
			if (e.getMessage().equals("not-valid")) {
				this.notificationService.generateNotification("danger", this, serialVersionUID,
						"This object isn't valid.");
			} else {
				this.notificationService.generateNotification("danger", this, serialVersionUID,
						"This object hasn't been updated.");
			}
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, serialVersionUID,
					"This object isn't in database.");
		}
		response.sendRedirect(request.getContextPath() + Variable.URL_COMPUTER);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idString = request.getParameter(Variable.GET_PARAMETER_ID);
		int id = 0;
		if (idString != null) {
			id = Integer.valueOf(idString);
		}
		try {
			ComputerDTO cDTO = this.computerService.getComputerById(id);
			request.setAttribute("computer", cDTO);
			List<CompanyDTO> listCompanies = this.companyService.getAllCompanies();

			request.setAttribute(Variable.COMPANY_LIST, listCompanies);

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_EDIT);
			rd.forward(request, response);
		} catch (ItemNotFoundException e) {
			this.notificationService.generateNotification("danger", this, serialVersionUID,
					"This object isn't in database.");
			response.sendRedirect(request.getContextPath() + Variable.URL_COMPUTER);
		}
	}
}
