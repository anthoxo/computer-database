package webapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import core.util.Variable;

@Controller
public class IndexController {

	@GetMapping({Variable.URL_ROOT, Variable.URL_INDEX})
	public String getIndex(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			if (auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
				return Variable.VIEW_INDEX;
			} else {
				return "redirect:/signin";
			}
		} else {
			return "redirect:/signin";
		}
	}
}
