package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	@GetMapping("/error")
	public String handleError(HttpServletRequest req, Model model, Exception exception) {
		return "error/error";
	}

	@GetMapping("/404")
	public String handleError404(HttpServletRequest req, Model model, Exception exception) {
		return "error/404";
	}

	@GetMapping("/500")
	public String handleError500(HttpServletRequest req, Model model, Exception exception) {
		return "error/500";
	}
}
