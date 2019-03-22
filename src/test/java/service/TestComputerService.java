package service;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.ComputerDAO;
import exception.DAOException;
import exception.ItemNotFoundException;
import model.Computer;

@ExtendWith(MockitoExtension.class)
public class TestComputerService {
	@Mock
	ComputerDAO computerDAO;

	@InjectMocks
	ComputerService computerService;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, DAOException {
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
	public void testGetByName() throws DAOException, ItemNotFoundException {
		Optional<Computer> o = Optional.of(new Computer());
		Mockito.when(computerDAO.get(Mockito.any())).thenReturn(o);
		this.computerService.getComputerByName("ouais");
		Mockito.verify(computerDAO).get(Mockito.anyString());
	}
}
