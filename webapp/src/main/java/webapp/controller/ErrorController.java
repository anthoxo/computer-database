package webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import core.util.Variable;

@Controller
public class ErrorController {

	@GetMapping("/error")
	public String handleError(HttpServletRequest req, Model model, Exception exception) {
		model.addAttribute(Variable.IS_USER, false);
		return Variable.VIEW_ERROR;
	}

	@GetMapping("/401")
	public String handleError401(HttpServletRequest req, Model model, Exception exception) {
		model.addAttribute(Variable.IS_USER, false);
		return "error/401";
	}

	@GetMapping("/403")
	public String handleError403(HttpServletRequest req, Model model, Exception exception) {
		model.addAttribute(Variable.IS_USER, false);
		return "error/403";
	}

	@GetMapping("/404")
	public String handleError404(HttpServletRequest req, Model model, Exception exception) {
		model.addAttribute(Variable.IS_USER, false);
		return Variable.VIEW_ERROR_404;
	}

	@GetMapping("/500")
	public String handleError500(HttpServletRequest req, Model model, Exception exception) {
		model.addAttribute(Variable.IS_USER, false);
		return Variable.VIEW_ERROR_500;
	}
}
