package controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import utils.Variable;

@Controller
public class LanguageController {

	CookieLocaleResolver localeResolver;

	public LanguageController(CookieLocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	@GetMapping(Variable.URL_FRENCH)
	public String getFrenchLanguage() {
		this.localeResolver.setDefaultLocale(Locale.FRENCH);
		return "redirect:" + Variable.URL_INDEX;
	}

	@GetMapping(Variable.URL_ENGLISH)
	public String getEnglishLanguage() {
		this.localeResolver.setDefaultLocale(Locale.ENGLISH);
		return "redirect:" + Variable.URL_INDEX;
	}
}
