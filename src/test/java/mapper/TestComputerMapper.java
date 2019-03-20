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

import model.Computer;

@ExtendWith(MockitoExtension.class)
public class TestComputerMapper {

	@Mock
	ResultSet rs;

	ComputerMapper computerMapper;

	@BeforeEach
	public void init() throws SQLException {
		computerMapper = ComputerMapper.getInstance();

		Mockito.doReturn(1).when(rs).getInt("id");
		Mockito.doReturn("Macbook Air").when(rs).getString("name");
		Mockito.doReturn(1).when(rs).getInt("company_id");
	}

	@Test
	public void testMap() throws SQLException {
		Mockito.doReturn(new Timestamp(0)).when(rs).getTimestamp("introduced");
		Mockito.doReturn(new Timestamp(1000)).when(rs).getTimestamp("discontinued");
		Computer computer = computerMapper.map(rs);
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Macbook Air");
		assertEquals(computer.getCompanyId(), 1);
	}

	@Test
	public void testMapWithNullDate() throws SQLException {
		Mockito.doReturn(null).when(rs).getTimestamp("introduced");
		Mockito.doReturn(null).when(rs).getTimestamp("discontinued");
		Computer computer = computerMapper.map(rs);
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Macbook Air");
		assertEquals(computer.getCompanyId(), 1);
	}
}
