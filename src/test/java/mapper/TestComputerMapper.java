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
		
		Mockito.when(rs.getInt("id")).thenReturn(1);
		Mockito.when(rs.getString("name")).thenReturn("Macbook Air");
		Mockito.when(rs.getTimestamp("introduced")).thenReturn(new Timestamp(0));
		Mockito.when(rs.getTimestamp("discontinued")).thenReturn(new Timestamp(0));
		Mockito.when(rs.getInt("company_id")).thenReturn(1);
	}
	
	@Test
	public void testMap() throws SQLException {
		Computer computer = computerMapper.map(rs);
		assertEquals(computer.getId(),1);
		assertEquals(computer.getName(),"Macbook Air");
		assertEquals(computer.getCompanyId(),1);
	}
}
