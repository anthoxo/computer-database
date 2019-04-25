package console.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import console.main.MainConfig;

@ExtendWith(MockitoExtension.class)
public class TestMainController {
	@InjectMocks
	private MainController mainController;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		mainController = context.getBean(MainController.class);
		context.close();
	}

	@Test
	public void testSelectDatabaseCompany() {
		assertTrue(mainController.selectDatabase("company"));
	}

	@Test
	public void testSelectDatabaseComputer() {
		assertTrue(mainController.selectDatabase("computer"));
	}

	@Test
	public void testSelectDatabaseBack() {
		assertTrue(mainController.selectDatabase("quit"));
		assertTrue(mainController.isLeaving());

	}

	@Test
	public void testSelectDatabaseBadAction1() {
		assertFalse(mainController.selectDatabase("bad_action"));
	}

	@Test
	public void testSelectDatabaseBadAction2() {
		assertFalse(mainController.selectDatabase("kfdblifd"));
	}

}
