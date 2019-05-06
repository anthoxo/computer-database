package webapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.model.Company;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import service.CompanyService;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyRestController {
	CompanyService companyService;

	public CompanyRestController(CompanyService companyService) {
		this.companyService = companyService;
	}

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        try {
			return new ResponseEntity<List<Company>>(this.companyService.getAllCompanies(), HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<List<Company>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompany(@RequestBody Company company, @PathVariable int id) {
        try {
        	this.companyService.deleteCompany(company);
			return new ResponseEntity<Company>(company, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
		} catch (ItemNotDeletedException e) {
			return new ResponseEntity<Company>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
