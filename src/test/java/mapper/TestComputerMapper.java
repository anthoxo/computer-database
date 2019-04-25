package mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import console.MainConfig;
import dto.ComputerDTO;
import exception.DAOException;
import model.Company;
import model.Computer;
import utils.Utils;

@ExtendWith(MockitoExtension.class)
public class TestComputerMapper {

	@Mock
	ResultSet rs;

	static AnnotationConfigApplicationContext context;

	ComputerMapper computerMapper;

	@BeforeAll
	public static void setUp() {
		context = new AnnotationConfigApplicationContext(MainConfig.class);
	}

	@AfterAll
	public static void shutDown() {
		context.close();
	}


	@BeforeEach
	public void init() {
		computerMapper = context.getBean(ComputerMapper.class);
	}

	public void initMap() throws SQLException {
		Mockito.doReturn(1).when(rs).getInt("id");
		Mockito.doReturn("Macbook Air").when(rs).getString("name");
		Mockito.doReturn(1).when(rs).getInt("company_id");
	}

	@Test
	public void testCreateDTO() {
		Company company = (new Company.Builder()).withName("Apple Inc.").build();
		Computer computer = (new Computer.Builder()).withId(1).withName("Macbook Air")
				.withIntroducedDate(Utils.stringToTimestamp("2000/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2020/01/01").get()).withCompany(company)
				.build();
		ComputerDTO computerDTO = computerMapper.createDTO(computer);
		assertEquals(computer.getId(), computerDTO.getId());
		assertEquals(computer.getName(), computerDTO.getName());
		assertEquals(computer.getIntroduced(), Utils.stringToTimestamp(computerDTO.getIntroducedDate()).get());
		assertEquals(computer.getDiscontinued(), Utils.stringToTimestamp(computerDTO.getDiscontinuedDate()).get());
		assertEquals(computer.getCompany().getName(), computerDTO.getCompanyName());
	}

	@Test
	public void testCreateBean() throws SQLException, DAOException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(0);
		computerDTO.setName("MacBook");
		computerDTO.setIntroducedDate("2010/01/01");
		computerDTO.setDiscontinuedDate("2020/01/01");
		Computer computer = computerMapper.createBean(computerDTO);
		assertEquals(computer.getId(), computerDTO.getId());
		assertEquals(computer.getName(), computerDTO.getName());
		assertEquals(computer.getIntroduced(), Utils.stringToTimestamp(computerDTO.getIntroducedDate()).get());
		assertEquals(computer.getDiscontinued(), Utils.stringToTimestamp(computerDTO.getDiscontinuedDate()).get());
	}

}
