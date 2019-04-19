package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import utils.Variable;

@Controller
public class ErrorController {

	@GetMapping(Variable.URL_ERROR)
	public String handleError(HttpServletRequest req, Model model, Exception exception) {
		return Variable.VIEW_ERROR;
	}

	@GetMapping(Variable.URL_ERROR_404)
	public String handleError404(HttpServletRequest req, Model model, Exception exception) {
		return Variable.VIEW_ERROR_404;
	}

	@GetMapping(Variable.URL_ERROR_500)
	public String handleError500(HttpServletRequest req, Model model, Exception exception) {
		return Variable.VIEW_ERROR_500;
	}
}
