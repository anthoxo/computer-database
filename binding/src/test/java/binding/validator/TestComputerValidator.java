package binding.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import binding.dto.ComputerDTO;

public class TestComputerValidator {
	static ComputerDTOValidator computerValidator;

	@BeforeAll
	public static void setUp() {
		computerValidator = new ComputerDTOValidator();
	}

	@Test
	public void testGoodComputer1() {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(1);
		computerDTO.setName("Ordi");
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(computerDTO, "computerDTO");
		computerValidator.validate(computerDTO, result);
		assertFalse(result.hasErrors());
	}

	@Test
	public void testGoodComputer2() {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(1);
		computerDTO.setName("Ordi");
		computerDTO.setIntroducedDate("2020/01/01");
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(computerDTO, "computerDTO");
		computerValidator.validate(computerDTO, result);
		assertFalse(result.hasErrors());
	}

	@Test
	public void testGoodComputer3() {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(1);
		computerDTO.setName("Ordi");
		computerDTO.setIntroducedDate("2020/01/01");
		computerDTO.setDiscontinuedDate("2030/01/01");
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(computerDTO, "computerDTO");
		computerValidator.validate(computerDTO, result);
		assertFalse(result.hasErrors());
	}

	@Test
	public void testBadComputer1() {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(1);
		computerDTO.setIntroducedDate("2020/01/01");
		computerDTO.setDiscontinuedDate("2030/01/01");
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(computerDTO, "computerDTO");
		computerValidator.validate(computerDTO, result);
		assertTrue(result.hasErrors());
	}

	@Test
	public void testBadComputer2() {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(1);
		computerDTO.setName("");
		computerDTO.setIntroducedDate("2020/01/01");
		computerDTO.setDiscontinuedDate("2030/01/01");
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(computerDTO, "computerDTO");
		computerValidator.validate(computerDTO, result);
		assertTrue(result.hasErrors());
	}

	@Test
	public void testBadComputer3() {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(1);
		computerDTO.setName("Ordi");
		computerDTO.setIntroducedDate("2035/01/01");
		computerDTO.setDiscontinuedDate("2030/01/01");
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(computerDTO, "computerDTO");
		computerValidator.validate(computerDTO, result);
		assertTrue(result.hasErrors());
	}
}
