package console.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import console.MainConfig;
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

	private List<ComputerDTO> computerList;

	@Mock
	private Page<ComputerDTO> computerPage;

	@Mock
	ComputerService computerService;

	@InjectMocks
	private ComputerController computerController;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

		computerList = new ArrayList<ComputerDTO>();
		for (int i = 0; i < 40; ++i) {
			ComputerDTO computerDTO = new ComputerDTO();
			computerDTO.setId(i + 1);
			computerDTO.setName("Computer_" + String.valueOf(i + 1));
			computerDTO.setCompanyName("Apple_" + String.valueOf(i + 1));
			computerList.add(computerDTO);
		}

		computerPage = new Page<ComputerDTO>(computerList);

		computerController = context.getBean(ComputerController.class);
		computerController.computerPage = computerPage;

		Field field = ComputerController.class.getDeclaredField("computerService");
		field.setAccessible(true);
		field.set(computerController, computerService);

		context.close();
	}

	@Test
	public void testSelectActionNext() {
		assertTrue(computerController.selectAction("next"));
	}

	@Test
	public void testSelectActionPrevious() {
		assertTrue(computerController.selectAction("previous"));
	}

	@Test
	public void testSelectActionBack() {
		assertTrue(computerController.selectAction("back"));
		assertTrue(computerController.isGoingBack());

	}

	@Test
	public void testSelectActionBadAction1() {
		assertFalse(computerController.selectAction("bad_action"));
	}

	@Test
	public void testSelectActionBadAction2() {
		assertFalse(computerController.selectAction("kfdblifd"));
	}

	@Test
	public void testCreateComputer() throws DAOException, ItemBadCreatedException {
		Mockito.doNothing().when(computerService).createComputer(Mockito.any(Computer.class));

		this.computerController.createComputer("Computer_1", "", "", "1");

		Mockito.verify(computerService).createComputer(Mockito.any(Computer.class));
	}

	@Test
	public void testUpdateComputer() throws DAOException, ItemNotUpdatedException, ItemNotFoundException {

		Mockito.doNothing().when(computerService).updateComputer(Mockito.any(Computer.class));

		this.computerController.updateComputer(1, "Computer_1", "", "", 1);

		Mockito.verify(computerService).updateComputer(Mockito.any(Computer.class));
	}

	@Test
	public void testDeleteComputer() throws DAOException, ItemNotFoundException, ItemNotDeletedException {

		Mockito.doNothing().when(computerService).deleteComputer(Mockito.any());

		this.computerController.deleteComputer(0);

		Mockito.verify(computerService).deleteComputer(Mockito.any());
	}

}
