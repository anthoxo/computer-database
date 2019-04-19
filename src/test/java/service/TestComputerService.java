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

import dao.ComputerDao;
import exception.DAOException;
import exception.ItemBadCreatedException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import mapper.ComputerMapper;
import model.Computer;
import utils.Utils.OrderByOption;
import validator.ComputerDTOValidator;

@ExtendWith(MockitoExtension.class)
public class TestComputerService {
	@Mock
	ComputerDao computerDao;

	@Mock
	ComputerMapper computerMapper;

	@InjectMocks
	ComputerService computerService;

	@BeforeEach
	public void init() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			DAOException {
		Field field = ComputerService.class.getDeclaredField("computerDao");
		field.setAccessible(true);
		field.set(computerService, computerDao);
		computerService.computerValidator = new ComputerDTOValidator();
	}

	@Test
	public void testGetAll() throws DAOException {
		this.computerService.getAllComputers();
		Mockito.verify(computerDao).getAll();
	}

	@Test
	public void testGetAllOrderBy() throws DAOException {
		this.computerService.getAllComputersOrderBy("name", OrderByOption.ASC);
		Mockito.verify(computerDao).getAllOrderBy("name", false);
	}

	@Test
	public void testGetByName() throws DAOException, ItemNotFoundException {
		Optional<Computer> computerOpt = Optional.of(new Computer.Builder().build());
		Mockito.when(computerDao.get(Mockito.anyString())).thenReturn(computerOpt);
		this.computerService.getComputerByName("ouais");
		Mockito.verify(computerDao).get(Mockito.anyString());
	}

	@Test
	public void testGetByNameThrow() throws DAOException {
		try {
			this.computerService.getComputerByName("ouais");
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerDao).get(Mockito.anyString());
		}
	}

	@Test
	public void testGetById() throws DAOException, ItemNotFoundException {
		Optional<Computer> computerOpt = Optional.of(new Computer.Builder().build());
		Mockito.when(computerDao.get(Mockito.anyInt())).thenReturn(computerOpt);
		this.computerService.getComputerById(1);
		Mockito.verify(computerDao).get(Mockito.anyInt());
	}

	@Test
	public void testGetByIdThrow() throws DAOException {
		try {
			this.computerService.getComputerById(1);
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerDao).get(Mockito.anyInt());
		}
	}

	@Test
	public void testCreate() throws DAOException, ItemBadCreatedException {
		Computer computer = new Computer.Builder().withName("oui").build();
		this.computerService.createComputer(computer);
		Mockito.verify(computerDao).create(Mockito.any());
	}

	@Test
	public void testUpdate() throws DAOException, ItemNotUpdatedException, ItemNotFoundException {
		Computer computer = new Computer.Builder().withName("oui").build();
		this.computerService.updateComputer(computer);
		Mockito.verify(computerDao).update(Mockito.any());
	}

	@Test
	public void testDelete() throws DAOException, ItemNotFoundException, ItemNotDeletedException {
		Optional<Computer> computerOpt = Optional.of(new Computer.Builder().build());
		Mockito.when(computerDao.get(Mockito.anyInt())).thenReturn(computerOpt);
		this.computerService.deleteComputer(computerOpt.get());
		Mockito.verify(computerDao).delete(Mockito.any());
	}

	@Test
	public void testDeleteThrow() throws DAOException, ItemNotDeletedException {
		try {
			this.computerService.deleteComputer(new Computer.Builder().build());
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerDao).get(Mockito.anyInt());
		}
	}

	@Test
	public void testGetPattern() throws DAOException {
		this.computerService.getComputersByPattern("ou");
		Mockito.verify(computerDao).getPattern("ou");
	}

	@Test
	public void testGetPatternOrderBy() throws DAOException {
		this.computerService.getComputersByPatternOrderBy("ou", "name", OrderByOption.ASC);
		Mockito.verify(computerDao).getPatternOrderBy("ou", "name", false);
	}
}
