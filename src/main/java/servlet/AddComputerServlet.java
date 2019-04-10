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

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dto.CompanyDTO;
import dto.ComputerDTO;
import exception.ItemBadCreatedException;
import main.MainConfig;
import service.CompanyService;
import service.ComputerService;
import service.NotificationService;
import utils.Variable;

@WebServlet("/computer/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -690181327611746612L;

	ComputerService computerService;
	CompanyService companyService;
	NotificationService notificationService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		AnnotationConfigApplicationContext applicationContext = MainConfig.getApplicationContext();
		this.companyService = applicationContext.getBean(CompanyService.class);
		this.computerService = applicationContext.getBean(ComputerService.class);
		this.notificationService = applicationContext.getBean(NotificationService.class);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(request.getParameter(Variable.GET_PARAMETER_NAME));
		computerDTO.setIntroducedDate(request.getParameter(Variable.GET_PARAMETER_INTRODUCED));
		computerDTO.setDiscontinuedDate(request.getParameter(Variable.GET_PARAMETER_DISCONTINUED));
		String companyIdStr = request.getParameter(Variable.GET_PARAMETER_COMPANY_ID);
		int companyId = 0;
		try {
			companyId = Integer.parseInt(companyIdStr);
		} catch (NumberFormatException e) {
			companyId = 0;
		}
		computerDTO.setCompanyId(companyId);
		try {
			this.computerService.createComputer(computerDTO);
			this.notificationService.generateNotification("success", this, serialVersionUID,
					"This object has been correctly created !");
		} catch (ItemBadCreatedException e) {
			if (e.getMessage().equals("not-valid")) {
				this.notificationService.generateNotification("danger", this, serialVersionUID,
						"This object isn't valid.");
			} else {
				this.notificationService.generateNotification("danger", this, serialVersionUID,
						"This object hasn't been created.");
			}
		}
		response.sendRedirect(request.getContextPath() + Variable.URL_COMPUTER);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<CompanyDTO> companyList = this.companyService.getAllCompanies();
		request.setAttribute(Variable.COMPANY_LIST, companyList);

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_ADD);
		rd.forward(request, response);
	}
}
