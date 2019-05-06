package webapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	public ComputerRestController(ComputerService computerService) {
		this.computerService = computerService;
	}

    @GetMapping
    public ResponseEntity<List<Computer>> getAll() {
        try {
			return new ResponseEntity<List<Computer>>(this.computerService.getAllComputers(), HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<List<Computer>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping(value = "")
    public ResponseEntity<Computer> postComputer(@RequestBody Computer computer) {
        try {
			this.computerService.createComputer(computer);
			return new ResponseEntity<Computer>(computer, HttpStatus.OK);
		} catch (ItemBadCreatedException e) {
			return new ResponseEntity<Computer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }


    @GetMapping("/{id}")
    public ResponseEntity<Computer> getComputer(@PathVariable int id) {
        try {
			return new ResponseEntity<Computer>(this.computerService.getComputerById(id), HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<Computer>(HttpStatus.NOT_FOUND);
		}
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Computer> updateComputer(@RequestBody Computer computer, @PathVariable int id) {
		try {
			this.computerService.updateComputer(computer);
	    	return new ResponseEntity<Computer>(computer, HttpStatus.OK);
		} catch (ItemNotUpdatedException e) {
	    	return new ResponseEntity<Computer>(HttpStatus.NOT_MODIFIED);
		} catch (ItemNotFoundException e) {
	    	return new ResponseEntity<Computer>(HttpStatus.NOT_FOUND);
		}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Computer> deleteComputer(@RequestBody Computer computer, @PathVariable int id) {
		try {
			this.computerService.deleteComputer(computer);
	    	return new ResponseEntity<Computer>(computer, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
	    	return new ResponseEntity<Computer>(HttpStatus.NOT_FOUND);
		} catch (ItemNotDeletedException e) {
	    	return new ResponseEntity<Computer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }


}
