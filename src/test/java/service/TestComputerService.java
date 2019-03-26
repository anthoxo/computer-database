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
import dto.ComputerDTO;
import exception.DAOException;
import exception.ItemBadCreatedException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import exception.ItemNotUpdatedException;
import model.Computer;

@ExtendWith(MockitoExtension.class)
public class TestComputerService {
	@Mock
	ComputerDAO computerDAO;

	@InjectMocks
	ComputerService computerService;

	@BeforeEach
	public void init() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			DAOException {
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
		Optional<Computer> o = Optional.of(new Computer.Builder().build());
		Mockito.when(computerDAO.get(Mockito.anyString())).thenReturn(o);
		this.computerService.getComputerByName("ouais");
		Mockito.verify(computerDAO).get(Mockito.anyString());
	}

	@Test
	public void testGetByNameThrow() throws DAOException {
		try {
			this.computerService.getComputerByName("ouais");
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerDAO).get(Mockito.anyString());
		}
	}

	@Test
	public void testGetById() throws DAOException, ItemNotFoundException {
		Optional<Computer> o = Optional.of(new Computer.Builder().build());
		Mockito.when(computerDAO.get(Mockito.anyInt())).thenReturn(o);
		this.computerService.getComputerById(1);
		Mockito.verify(computerDAO).get(Mockito.anyInt());
	}

	@Test
	public void testGetByIdThrow() throws DAOException {
		try {
			this.computerService.getComputerById(1);
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerDAO).get(Mockito.anyInt());
		}
	}

	@Test
	public void testCreate() throws DAOException, ItemBadCreatedException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName("oui");
		this.computerService.createComputer(computerDTO);
		Mockito.verify(computerDAO).create(Mockito.any());
	}

	@Test
	public void testUpdate() throws DAOException, ItemNotUpdatedException, ItemNotFoundException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName("oui");
		this.computerService.updateComputer(computerDTO);
		Mockito.verify(computerDAO).update(Mockito.any());
	}

	@Test
	public void testDelete() throws DAOException, ItemNotFoundException, ItemNotDeletedException {
		Optional<Computer> o = Optional.of(new Computer.Builder().build());
		Mockito.when(computerDAO.get(Mockito.anyInt())).thenReturn(o);
		this.computerService.deleteComputer(new ComputerDTO());
		Mockito.verify(computerDAO).delete(Mockito.any());
	}

	@Test
	public void testDeleteThrow() throws DAOException, ItemNotDeletedException {
		try {
			this.computerService.deleteComputer(new ComputerDTO());
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerDAO).get(Mockito.anyInt());
		}
	}

	@Test
	public void testGetPattern() throws DAOException {
		this.computerService.getComputersByPattern("ou");
		Mockito.verify(computerDAO).getPattern("ou");
	}

}
