package webapp.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import binding.dto.UserDTO;
import binding.mapper.UserMapper;
import binding.validator.UserDTOValidator;
import core.util.Variable;
import persistence.exception.ItemBadCreatedException;
import service.NotificationService;
import service.UserService;

@Controller
public class LoginController {

	UserService userService;
	NotificationService notificationService;
	UserMapper userMapper;
	MessageSource messageSource;

	public LoginController(UserService userService, NotificationService notificationService, UserMapper userMapper,
			MessageSource messageSource) {
		this.userService = userService;
		this.notificationService = notificationService;
		this.userMapper = userMapper;
		this.messageSource = messageSource;
	}

	@GetMapping("/signin")
	public String getSignIn(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		if (this.notificationService.isNotifying()) {
			model.addAttribute(Variable.IS_NOTIFYING, true);
			model.addAttribute(Variable.NOTIFICATION, this.notificationService.getNotification());
			this.notificationService.clean();
		} else {
			model.addAttribute(Variable.IS_NOTIFYING, false);
		}
		return "signin";
	}

	@PostMapping("/signin")
	public String postSignIn(@Validated @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result) {
		System.out.println(userDTO.getEmail());
		return "signin";
	}

	@GetMapping("/signup")
	public String getSignUp(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "signup";
	}

	@PostMapping("/signup")
	public String postSignUp(@Validated @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
			Locale locale) {
		String levelNotification = Variable.SUCCESS;
		String messageNotification = "login.signup.notification.good";

		userDTO.setRole("USER");
		UserDTOValidator userValidator = new UserDTOValidator();
		userValidator.validate(userDTO, result);

		if (result.hasErrors()) {
			levelNotification = Variable.DANGER;
			messageNotification = "login.signup.notification.not_valid";
		} else {
			try {
				this.userService.createUser(this.userMapper.createEntity(userDTO));
			} catch (ItemBadCreatedException e) {
				levelNotification = Variable.DANGER;
				messageNotification = "login.signup.notification.not_created";
			}
		}
		this.notificationService.generateNotification(levelNotification, this,
				this.messageSource.getMessage(messageNotification, null, locale));

		return "redirect:/signin";
	}
}
