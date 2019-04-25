package webapp;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import webapp.controller.ControllerConfig;

@Configuration
@ComponentScan(basePackageClasses = { ControllerConfig.class })
@EnableWebMvc
public class WebappConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/views/");
		bean.setSuffix(".jsp");
		bean.setViewClass(JstlView.class);
		return bean;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver locale = new CookieLocaleResolver();
		locale.setDefaultLocale(Locale.ENGLISH);
		locale.setCookieName("myAppCookie");
		locale.setCookieMaxAge(3600);
		return locale;
	}
}
