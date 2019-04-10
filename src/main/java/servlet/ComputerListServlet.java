package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dto.ComputerDTO;
import main.MainConfig;
import model.Page;
import service.ComputerService;
import service.NotificationService;
import utils.Utils.OrderByOption;
import utils.Variable;

@WebServlet("/computer")
public class ComputerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerService computerService;
	NotificationService notificationService;

	Page<ComputerDTO> computerPage;
	OrderByOption orderByOption;
	String orderColumn;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		AnnotationConfigApplicationContext applicationContext = MainConfig.getApplicationContext();
		this.computerService = applicationContext.getBean(ComputerService.class);
		this.notificationService = applicationContext.getBean(NotificationService.class);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String indexPage = request.getParameter(Variable.GET_PARAMETER_ID);
			String orderColumnTmp;
			int index;
			if (indexPage == null) {
				index = 0;
				orderColumnTmp = request.getParameter(Variable.GET_ORDER_BY);
				if (orderColumnTmp != null) {
					switch (this.orderByOption) {
					case ASC:
						this.orderByOption = OrderByOption.DESC;
						break;
					default:
						this.orderByOption = OrderByOption.ASC;
						break;
					}
					if (!orderColumnTmp.equals(orderColumn)) {
						this.orderByOption = OrderByOption.ASC;
					}
					this.orderColumn = orderColumnTmp;
					this.computerPage = new Page<ComputerDTO>(
							computerService.getAllComputersOrderBy(orderColumn, orderByOption));
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

			if (this.notificationService.isNotifying()) {
				request.setAttribute(Variable.NOTIFICATION, true);
				request.setAttribute(Variable.MSG_NOTIFICATION, this.notificationService.getMessage());
				request.setAttribute(Variable.LVL_NOTIFICATION, this.notificationService.getLevel());
				this.notificationService.clean();
			} else {
				request.setAttribute(Variable.NOTIFICATION, false);
			}

			RequestDispatcher rd = this.getServletContext().getRequestDispatcher(Variable.VIEW_COMPUTER);
			rd.forward(request, response);
		} catch (ServletException e) {
			throw new ServletException(e);
		}
	}
}
