package webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import binding.dto.CompanyDTO;
import binding.mapper.CompanyMapper;
import core.model.Company;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import service.CompanyService;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyRestController {
	CompanyService companyService;
	CompanyMapper companyMapper;

	public CompanyRestController(CompanyService companyService, CompanyMapper companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}

	@Secured("ROLE_USER")
	@GetMapping
	public ResponseEntity<List<CompanyDTO>> getAll() {
		try {
			List<CompanyDTO> listCompanies = this.companyService.getAllCompanies().stream()
					.map((Company c) -> this.companyMapper.createDTO(c)).collect(Collectors.toList());
			return new ResponseEntity<>(listCompanies, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_USER")
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDTO> getCompany(@PathVariable int id) {
		try {
			CompanyDTO companyDTO = this.companyMapper.createDTO(this.companyService.getCompanyById(id));
			return new ResponseEntity<>(companyDTO, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable int id) {
		try {
			Company company = this.companyService.getCompanyById(id);
			if (company != null) {
				this.companyService.deleteCompany(company);
				return new ResponseEntity<>(this.companyMapper.createDTO(company), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ItemNotDeletedException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
