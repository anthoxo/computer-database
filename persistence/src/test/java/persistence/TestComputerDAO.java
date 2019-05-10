package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import core.model.Computer;
import core.util.Utils;
import persistence.exception.ItemNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TestComputerDAO {

	static AnnotationConfigApplicationContext context;

	ComputerRepository computerRepository;

	JdbcTemplate jdbcTemplate;
	JdbcTemplate jdbcTemplateTest;

	@Test
	public void testGetById() throws SQLException {
		Optional<Computer> computerOpt = this.computerRepository.findById(1);
		if (computerOpt.isPresent()) {
			Computer computer = computerOpt.get();
			assertEquals(1, computer.getId());
			assertEquals("MacBook Pro 15.4 inch", computer.getName());
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetByName() throws SQLException {
		Optional<Computer> computerOpt = this.computerRepository.findByName("CM-2a");
		if (computerOpt.isPresent()) {
			Computer computer = computerOpt.get();
			assertEquals(2, computer.getId(), 2);
			assertEquals("CM-2a", computer.getName());
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAll() throws SQLException {
		List<Computer> list = this.computerRepository.findAll();
		assertEquals(4, list.size());
	}

	@Test
	public void testGetAllOrderByName() throws SQLException {
		List<String> l = Arrays.asList("CM-200", "CM-2a", "CM-5e", "MacBook Pro 15.4 inch");
		List<Computer> list = this.computerRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		for (int i = 0; i < 4; ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testGetPattern() throws SQLException {
		List<String> l = Arrays.asList("CM-2a", "CM-200");
		List<Computer> list = this.computerRepository.findByNameContaining("-2");
		for (int i = 0; i < l.size(); ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testGetPatternOrderBy() throws SQLException {
		List<String> l = Arrays.asList("CM-200", "CM-2a");
		List<Computer> list = this.computerRepository.findByNameContaining("-2", Sort.by(Sort.Direction.ASC, "name"));
		for (int i = 0; i < l.size(); ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testCreateAndDelete() throws SQLException {
		Computer computer = new Computer.Builder().withName("Computer_test").build();
		this.computerRepository.save(computer);
		List<Computer> list = this.computerRepository.findAll();
		assertEquals(5, list.size());
		this.computerRepository.delete(this.computerRepository.findByName("Computer_test").get());
		list = this.computerRepository.findAll();
		assertEquals(4, list.size());
	}

	@Test
	public void testCreateAndUpdate() throws SQLException, ItemNotFoundException {
		Computer computer = new Computer.Builder().withName("Computer_test").build();
		this.computerRepository.save(computer);
		List<Computer> list = this.computerRepository.findAll();
		assertEquals(5, list.size());
		computer = this.computerRepository.findByName("Computer_test").get();
		computer.setIntroduced(Utils.stringToTimestamp("1999/01/01").get());
		computer.setDiscontinued(Utils.stringToTimestamp("2000/01/01").get());
		this.computerRepository.save(computer);
		computer = this.computerRepository.findByName("Computer_test").get();
		assertEquals("Computer_test", computer.getName());
		assertEquals(Utils.stringToTimestamp("1999/01/01").get(), computer.getIntroduced());
		assertEquals(Utils.stringToTimestamp("2000/01/01").get(), computer.getDiscontinued());
		this.computerRepository.delete(this.computerRepository.findByName("Computer_test").get());
		list = this.computerRepository.findAll();
		assertEquals(4, list.size());
	}
}
