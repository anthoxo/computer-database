package com.excilys.computerdatabase.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestMainController {
	@InjectMocks
	private MainController mainController;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		mainController = new MainController();

	}

	@Test
	public void testSelectDatabaseCompany() {
		boolean result = mainController.selectDatabase("company");
		assertTrue(result);
	}

	@Test
	public void testSelectDatabaseComputer() {
		boolean result = mainController.selectDatabase("computer");
		assertTrue(result);
	}

	@Test
	public void testSelectDatabaseBack() {
		boolean result = mainController.selectDatabase("quit");
		assertTrue(result);
		assertTrue(mainController.isLeaving());

	}

	@Test
	public void testSelectDatabaseBadAction1() {
		boolean result = mainController.selectDatabase("bad_action");
		assertTrue(result == false);
	}

	@Test
	public void testSelectDatabaseBadAction2() {
		boolean result = mainController.selectDatabase("kfdblifd");
		assertTrue(result == false);
	}

}
