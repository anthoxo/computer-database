package webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import core.util.Variable;

@Controller
public class IndexController {

	@GetMapping({Variable.URL_ROOT, Variable.URL_INDEX})
	public String getIndex(Model model) {
		return Variable.VIEW_INDEX;
	}
}
