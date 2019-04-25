package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import dao.ComputerRepository;
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
	ComputerRepository computerRepository;

	@Mock
	ComputerMapper computerMapper;

	@InjectMocks
	ComputerService computerService;

	@BeforeEach
	public void init() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			DAOException {
		Field field = ComputerService.class.getDeclaredField("computerRepository");
		field.setAccessible(true);
		field.set(computerService, computerRepository);
		computerService.computerValidator = new ComputerDTOValidator();
	}

	@Test
	public void testGetAll() throws ItemNotFoundException {
		this.computerService.getAllComputers();
		Mockito.verify(computerRepository).findAll();
	}

	@Test
	public void testGetAllOrderBy() throws ItemNotFoundException {
		this.computerService.getAllComputersOrderBy("name", OrderByOption.ASC);
		Mockito.verify(computerRepository).findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Test
	public void testGetByName() throws ItemNotFoundException {
		List<Computer> listComputers = new ArrayList<Computer>();
		listComputers.add(new Computer.Builder().build());
		Mockito.when(computerRepository.findByName(Mockito.anyString())).thenReturn(listComputers);
		this.computerService.getComputerByName("ouais");
		Mockito.verify(computerRepository).findByName(Mockito.anyString());
	}

	@Test
	public void testGetByNameThrow() throws DAOException {
		try {
			this.computerService.getComputerByName("ouais");
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerRepository).findByName(Mockito.anyString());
		}
	}

	@Test
	public void testGetById() throws DAOException, ItemNotFoundException {
		Optional<Computer> computerOpt = Optional.of(new Computer.Builder().build());
		Mockito.when(computerRepository.findById(Mockito.anyInt())).thenReturn(computerOpt);
		this.computerService.getComputerById(1);
		Mockito.verify(computerRepository).findById(Mockito.anyInt());
	}

	@Test
	public void testGetByIdThrow() throws DAOException {
		try {
			this.computerService.getComputerById(1);
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerRepository).findById(Mockito.anyInt());
		}
	}

	@Test
	public void testCreate() throws DAOException, ItemBadCreatedException {
		Computer computer = new Computer.Builder().withName("oui").build();
		this.computerService.createComputer(computer);
		Mockito.verify(computerRepository).save(Mockito.any());
	}

	@Test
	public void testUpdate() throws DAOException, ItemNotUpdatedException, ItemNotFoundException {
		Computer computer = new Computer.Builder().withName("oui").build();
		this.computerService.updateComputer(computer);
		Mockito.verify(computerRepository).save(Mockito.any());
	}

	@Test
	public void testDelete() throws DAOException, ItemNotFoundException, ItemNotDeletedException {
		Optional<Computer> computerOpt = Optional.of(new Computer.Builder().build());
		Mockito.when(computerRepository.findById(Mockito.anyInt())).thenReturn(computerOpt);
		this.computerService.deleteComputer(computerOpt.get());
		Mockito.verify(computerRepository).delete(Mockito.any());
	}

	@Test
	public void testDeleteThrow() throws ItemNotDeletedException {
		try {
			this.computerService.deleteComputer(new Computer.Builder().build());
		} catch (ItemNotFoundException e) {
			Mockito.verify(computerRepository).findById(Mockito.anyInt());
		}
	}

	@Test
	public void testGetPattern() throws ItemNotFoundException {
		this.computerService.getComputersByPattern("ou");
		Mockito.verify(computerRepository).findByNameContaining("ou");
	}

	@Test
	public void testGetPatternOrderBy() throws ItemNotFoundException {
		this.computerService.getComputersByPatternOrderBy("ou", "name", OrderByOption.ASC);
		Mockito.verify(computerRepository).findByNameContaining("ou", Sort.by(Sort.Direction.ASC, "name"));
	}
}
