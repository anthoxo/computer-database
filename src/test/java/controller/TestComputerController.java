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

import dto.ComputerDTO;
import exception.DAOException;
import exception.ItemBadCreatedException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import model.Computer;
import model.Page;
import service.ComputerService;

@ExtendWith(MockitoExtension.class)
public class TestComputerController {

	@Mock
	private Computer computer;

	private List<ComputerDTO> listComputers;

	@Mock
	private Page<ComputerDTO> pageComputers;

	@Mock
	ComputerService computerService;


	@InjectMocks
	private ComputerController computerController;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {



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

		Field field = ComputerController.class.getDeclaredField("computerService");
		field.setAccessible(true);
		field.set(computerController, computerService);
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
	public void testCreateComputer() throws DAOException, ItemBadCreatedException {
		Mockito.doNothing().when(computerService).createComputer(Mockito.any(ComputerDTO.class));

		this.computerController.createComputer("Computer_1", "", "", 1);

		Mockito.verify(computerService).createComputer(Mockito.any(ComputerDTO.class));
	}

	@Test
	public void testUpdateComputer() throws DAOException, ItemNotUpdatedException, ItemNotFoundException {

		Mockito.doNothing().when(computerService).updateComputer(Mockito.any(ComputerDTO.class));

		this.computerController.updateComputer(1, "Computer_1", "", "", 1);

		Mockito.verify(computerService).updateComputer(Mockito.any(ComputerDTO.class));
	}

	@Test
	public void testDeleteComputer() throws DAOException, ItemNotFoundException, ItemNotDeletedException {

		Mockito.doNothing().when(computerService).deleteComputer(Mockito.any());

		this.computerController.deleteComputer(0);

		Mockito.verify(computerService).deleteComputer(Mockito.any());
	}

}
