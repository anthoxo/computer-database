package mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.CompanyDAO;
import dao.DAOFactory;
import dto.ComputerDTO;
import exception.DAOException;
import model.Company;
import model.Computer;
import utils.Utils;

@ExtendWith(MockitoExtension.class)
public class TestComputerMapper {

	@Mock
	ResultSet rs;

	@Mock
	DAOFactory dao;

	@Mock
	CompanyDAO companyDAO;

	ComputerMapper computerMapper;

	@BeforeEach
	public void init() {
		computerMapper = ComputerMapper.getInstance();

	}

	public void initMap() throws SQLException {
		Mockito.doReturn(1).when(rs).getInt("id");
		Mockito.doReturn("Macbook Air").when(rs).getString("name");
		Mockito.doReturn(1).when(rs).getInt("company_id");
	}

	@Test
	public void testMap() throws SQLException {
		initMap();
		Mockito.doReturn(new Timestamp(0)).when(rs).getTimestamp("introduced");
		Mockito.doReturn(new Timestamp(1000)).when(rs).getTimestamp("discontinued");
		Computer computer = computerMapper.map(rs);
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Macbook Air");
		assertEquals(computer.getCompanyId(), 1);
	}

	@Test
	public void testMapWithNullDate() throws SQLException {
		initMap();
		Mockito.doReturn(null).when(rs).getTimestamp("introduced");
		Mockito.doReturn(null).when(rs).getTimestamp("discontinued");
		Computer computer = computerMapper.map(rs);
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Macbook Air");
		assertEquals(computer.getCompanyId(), 1);
	}

	@Test
	public void testCreateDTO() {
		Company company = (new Company.Builder()).withName("Apple Inc.").build();
		Computer computer = (new Computer.Builder()).withId(1).withName("Macbook Air")
				.withIntroducedDate(Utils.stringToTimestamp("2000/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2020/01/01").get()).withCompanyId(1).withCompany(company)
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
