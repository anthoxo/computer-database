package console.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import binding.dto.CompanyDTO;
import binding.dto.ComputerDTO;
import core.model.User;

@Component
public class RestController {

	private Client client;
	private WebTarget webTarget;

	private static Logger logger = LoggerFactory.getLogger(RestController.class);

	private RestController() {
		this.client = ClientBuilder.newClient().property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		this.webTarget = client.target("http://localhost:8080/api/v1");
	}

	public List<CompanyDTO> getAllCompanies(User user) {
		String result = this.webTarget.path("/company").request().header("Authorization", "Bearer " + user.getToken())
				.get(String.class);
		ObjectMapper obj = new ObjectMapper();
		try {
			return obj.readValue(result, new TypeReference<List<CompanyDTO>>() {
			});
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			return new ArrayList<CompanyDTO>();
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			return new ArrayList<CompanyDTO>();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return new ArrayList<CompanyDTO>();
		}
	}

	public CompanyDTO getCompany(User user, int id) {
		try {
			String result = this.webTarget.path("/company/" + id).request()
					.header("Authorization", "Bearer " + user.getToken()).get(String.class);
			ObjectMapper obj = new ObjectMapper();
			return obj.readValue(result, CompanyDTO.class);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			return new CompanyDTO();
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			return new CompanyDTO();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return new CompanyDTO();
		}
	}

	public boolean deleteCompany(User user, CompanyDTO companyDTO) {
		int statusCode = this.webTarget.path("/company/" + companyDTO.getId()).request()
				.header("Authorization", "Bearer " + user.getToken()).delete().getStatus();
		return statusCode == 200;
	}

	public List<ComputerDTO> getAllComputers(User user) {
		String result = this.webTarget.path("/computer").request().header("Authorization", "Bearer " + user.getToken())
				.get(String.class);
		ObjectMapper obj = new ObjectMapper();
		try {
			return obj.readValue(result, new TypeReference<List<ComputerDTO>>() {
			});
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			return new ArrayList<ComputerDTO>();
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			return new ArrayList<ComputerDTO>();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return new ArrayList<ComputerDTO>();
		}
	}

	public boolean addComputer(User user, ComputerDTO computerDTO) {
		ObjectMapper obj = new ObjectMapper();
		try {
			String json = obj.writeValueAsString(computerDTO);
			return this.webTarget.path("/computer").request().header("Authorization", "Bearer " + user.getToken())
					.post(Entity.entity(json, MediaType.APPLICATION_JSON)).getStatus() == 200;
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public ComputerDTO getComputer(User user, int id) {
		try {
			String result = this.webTarget.path("/computer/" + id).request(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + user.getToken()).get(String.class);
			ObjectMapper obj = new ObjectMapper();
			return obj.readValue(result, ComputerDTO.class);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			return new ComputerDTO();
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			return new ComputerDTO();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return new ComputerDTO();
		}
	}

	public boolean updateComputer(User user, ComputerDTO computerDTO) {
		ObjectMapper obj = new ObjectMapper();
		try {
			String json = obj.writeValueAsString(computerDTO);
			return this.webTarget.path("/computer/" + computerDTO.getId()).request()
					.header("Authorization", "Bearer " + user.getToken())
					.build("PATCH", Entity.entity(json, MediaType.APPLICATION_JSON))
					.invoke().getStatus() == 200;
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteComputer(User user, ComputerDTO computerDTO) {
		return this.webTarget.path("/computer/" + computerDTO.getId()).request()
				.header("Authorization", "Bearer " + user.getToken())
				.delete().getStatus() == 200;
	}

}
