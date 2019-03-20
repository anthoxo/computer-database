package service;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.ComputerDAO;
import exception.DAOException;

@ExtendWith(MockitoExtension.class)
public class TestComputerService {
	@Mock
	ComputerDAO computerDAO;

	@InjectMocks
	ComputerService computerService;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = ComputerService.class.getDeclaredField("computerDAO");
		field.setAccessible(true);
		field.set(computerService, computerDAO);
	}

	@Test
	public void testGetAll() throws DAOException {
		this.computerService.getAllComputers();
		Mockito.verify(computerDAO).getAll();
	}

	@Test
	public void testGetByName() throws DAOException {
		this.computerService.getComputerByName("ouais");
		Mockito.verify(computerDAO).get(Mockito.anyString());
	}




}
