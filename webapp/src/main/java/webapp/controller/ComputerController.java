package webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import binding.dto.ComputerDTO;
import binding.mapper.ComputerMapper;
import core.model.Computer;
import persistence.exception.ItemBadCreatedException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import persistence.exception.ItemNotUpdatedException;
import service.ComputerService;

@RestController
@RequestMapping("/api/v1/computers")
public class ComputerController {
	ComputerService computerService;
	ComputerMapper computerMapper;

	public ComputerController(ComputerService computerService, ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}

	@Secured("ROLE_USER")
	@GetMapping
	public ResponseEntity<List<ComputerDTO>> getAll() {
		try {
			List<ComputerDTO> listComputers = this.computerService.getAllComputers().stream()
					.map((Computer c) -> this.computerMapper.createDTO(c)).collect(Collectors.toList());
			return new ResponseEntity<>(listComputers, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "")
	public ResponseEntity<ComputerDTO> addComputer(@RequestBody ComputerDTO computerDTO)
			throws ItemBadCreatedException {
		this.computerService.createComputer(this.computerMapper.createEntity(computerDTO));
		return new ResponseEntity<>(computerDTO, HttpStatus.OK);
	}

	@Secured("ROLE_USER")
	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTO> getComputer(@PathVariable int id) throws ItemNotFoundException {
		ComputerDTO computerDTO = this.computerMapper.createDTO(this.computerService.getComputerById(id));
		return new ResponseEntity<>(computerDTO, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PatchMapping("/{id}")
	public ResponseEntity<ComputerDTO> updateComputer(@RequestBody ComputerDTO computerDTO, @PathVariable int id)
			throws ItemNotUpdatedException, ItemNotFoundException {
		Computer computer = this.computerMapper.createEntity(computerDTO);
		this.computerService.updateComputer(computer);
		return new ResponseEntity<>(computerDTO, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteComputer(@PathVariable int id)
			throws ItemNotFoundException, ItemNotDeletedException {
		Computer computer = this.computerService.getComputerById(id);
		if (computer != null) {
			this.computerService.deleteComputer(computer);
			return new ResponseEntity<>("Computer deleted.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
