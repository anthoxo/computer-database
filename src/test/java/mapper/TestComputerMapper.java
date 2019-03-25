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
		Computer c = new Computer();
		c.setName("Macbook Air");
		c.setId(1);
		c.setCompanyId(1);
		Company company = new Company();
		company.setName("Apple Inc.");
		c.setIntroduced(Utils.stringToTimestamp("2000/01/01").get());
		c.setDiscontinued(Utils.stringToTimestamp("2020/01/01").get());
		c.setCompany(company);
		ComputerDTO cDTO = computerMapper.createDTO(c);
		assertEquals(c.getId(), cDTO.getId());
		assertEquals(c.getName(), cDTO.getName());
		assertEquals(c.getIntroduced(), Utils.stringToTimestamp(cDTO.getIntroducedDate()).get());
		assertEquals(c.getDiscontinued(), Utils.stringToTimestamp(cDTO.getDiscontinuedDate()).get());
		assertEquals(c.getCompany().getName(), cDTO.getCompanyName());
	}

	@Test
	public void testCreateBean() throws SQLException, DAOException {
		ComputerDTO cDTO = new ComputerDTO();
		cDTO.setId(0);
		cDTO.setName("MacBook");
		cDTO.setIntroducedDate("2010/01/01");
		cDTO.setDiscontinuedDate("2020/01/01");
		Computer c = computerMapper.createBean(cDTO);
		assertEquals(c.getId(), cDTO.getId());
		assertEquals(c.getName(), cDTO.getName());
		assertEquals(c.getIntroduced(), Utils.stringToTimestamp(cDTO.getIntroducedDate()).get());
		assertEquals(c.getDiscontinued(), Utils.stringToTimestamp(cDTO.getDiscontinuedDate()).get());
	}

}
