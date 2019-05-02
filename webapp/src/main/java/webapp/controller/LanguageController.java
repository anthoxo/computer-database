package webapp.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Controller
public class LanguageController {

	CookieLocaleResolver localeResolver;

	public LanguageController(CookieLocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	@GetMapping("/fr")
	public String getFrenchLanguage() {
		this.localeResolver.setDefaultLocale(Locale.FRENCH);
		return "redirect:/index";
	}

	@GetMapping("/en")
	public String getEnglishLanguage() {
		this.localeResolver.setDefaultLocale(Locale.ENGLISH);
		return "redirect:/index";
	}
}
