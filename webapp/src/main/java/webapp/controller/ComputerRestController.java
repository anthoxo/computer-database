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
@RequestMapping("/api/v1/computer")
public class ComputerRestController {
	ComputerService computerService;
	ComputerMapper computerMapper;

	public ComputerRestController(ComputerService computerService, ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}

	@Secured("ROLE_USER")
	@GetMapping
	public ResponseEntity<List<ComputerDTO>> getAll() {
		try {
			List<ComputerDTO> listComputers = this.computerService.getAllComputers().stream()
					.map((Computer c) -> this.computerMapper.createDTO(c)).collect(Collectors.toList());
			return new ResponseEntity<List<ComputerDTO>>(listComputers, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<List<ComputerDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "")
	public ResponseEntity<ComputerDTO> addComputer(@RequestBody ComputerDTO computerDTO) {
		try {
			this.computerService.createComputer(this.computerMapper.createEntity(computerDTO));
			return new ResponseEntity<ComputerDTO>(computerDTO, HttpStatus.OK);
		} catch (ItemBadCreatedException e) {
			return new ResponseEntity<ComputerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_USER")
	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTO> getComputer(@PathVariable int id) {
		try {
			ComputerDTO computerDTO = this.computerMapper.createDTO(this.computerService.getComputerById(id));
			return new ResponseEntity<ComputerDTO>(computerDTO, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<ComputerDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@Secured("ROLE_ADMIN")
	@PatchMapping("/{id}")
	public ResponseEntity<ComputerDTO> updateComputer(@RequestBody ComputerDTO computerDTO, @PathVariable int id) {
		try {
			Computer computer = this.computerMapper.createEntity(computerDTO);
			this.computerService.updateComputer(computer);
			return new ResponseEntity<ComputerDTO>(computerDTO, HttpStatus.OK);
		} catch (ItemNotUpdatedException e) {
			return new ResponseEntity<ComputerDTO>(HttpStatus.NOT_MODIFIED);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<ComputerDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<ComputerDTO> deleteComputer(@RequestBody ComputerDTO computerDTO, @PathVariable int id) {
		try {
			Computer computer = this.computerMapper.createEntity(computerDTO);
			this.computerService.deleteComputer(computer);
			return new ResponseEntity<ComputerDTO>(computerDTO, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<ComputerDTO>(HttpStatus.NOT_FOUND);
		} catch (ItemNotDeletedException e) {
			return new ResponseEntity<ComputerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
