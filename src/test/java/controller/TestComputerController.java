package controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.CompanyDAO;
import dao.ComputerDAO;
import dao.DAOFactory;
import dto.ComputerDTO;
import exception.DAOException;
import model.Computer;
import model.Page;

@ExtendWith(MockitoExtension.class)
public class TestComputerController {

	@Mock
	private Computer computer;

	private List<ComputerDTO> listComputers;

	@Mock
	private Page<ComputerDTO> pageComputers;

	@Mock
	private DAOFactory dao;

	@Mock
	private ComputerDAO computerDAO;

	@Mock
	private CompanyDAO companyDAO;

	@InjectMocks
	private ComputerController computerController;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		DAOFactory.getInstance();

		listComputers = new ArrayList<ComputerDTO>();
		for (int i = 0; i < 40; ++i) {
			ComputerDTO c = new ComputerDTO();
			c.setId(i + 1);
			c.setName("Computer_" + String.valueOf(i + 1));
			c.setCompanyName("Apple_" + String.valueOf(i + 1));
			listComputers.add(c);
		}

		pageComputers = new Page<ComputerDTO>(listComputers);

		computerController = new ComputerController();
		computerController.computerPage = pageComputers;

		Field field = ComputerController.class.getDeclaredField("DAO");
		field.setAccessible(true);
		field.set(computerController, dao);
	}

	@Test
	public void testSelectActionNext() {
		boolean result = computerController.selectAction("next");
		assertTrue(result);
	}

	@Test
	public void testSelectActionPrevious() {
		boolean result = computerController.selectAction("previous");
		assertTrue(result);
	}

	@Test
	public void testSelectActionBack() {
		boolean result = computerController.selectAction("back");
		assertTrue(result);
		assertTrue(computerController.isGoingBack());

	}

	@Test
	public void testSelectActionBadAction1() {
		boolean result = computerController.selectAction("bad_action");
		assertTrue(result == false);
	}

	@Test
	public void testSelectActionBadAction2() {
		boolean result = computerController.selectAction("kfdblifd");
		assertTrue(result == false);
	}

	@Test
	public void testCreateComputer() throws DAOException {
		Mockito.when(dao.getComputerDAO()).thenReturn(computerDAO);
		Mockito.when(dao.getCompanyDAO()).thenReturn(companyDAO);

		Mockito.doReturn(true).when(computerDAO).create(Mockito.any(Computer.class));

		this.computerController.createComputer("Computer_1", "", "", "");

		Mockito.verify(computerDAO).create(Mockito.any(Computer.class));
	}

	@Test
	public void testUpdateComputer() throws DAOException {
		Mockito.when(dao.getComputerDAO()).thenReturn(computerDAO);
		Mockito.doReturn(true).when(computerDAO).update(Mockito.any(Computer.class));

		this.computerController.updateComputer(new ComputerDTO(), "Computer_1", "", "", "");

		Mockito.verify(computerDAO).update(Mockito.any(Computer.class));
	}

	@Test
	public void testDeleteComputer() throws DAOException {
		Mockito.when(dao.getComputerDAO()).thenReturn(computerDAO);
		Mockito.doReturn(true).when(computerDAO).delete(Mockito.any(Computer.class));

		this.computerController.deleteComputer(new ComputerDTO());

		Mockito.verify(computerDAO).delete(Mockito.any(Computer.class));
	}

}
