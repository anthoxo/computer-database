package console.controller;

import java.io.IOException;
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

import binding.dto.ComputerDTO;
import binding.mapper.ComputerMapper;
import core.model.Page;
import core.model.User;
import core.util.Utils;
import core.util.Utils.ChoiceActionPage;
import persistence.exception.ItemBadCreatedException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import persistence.exception.ItemNotUpdatedException;

@Component
public class ComputerController {

	ComputerMapper computerMapper;

	private Client client;
	private WebTarget webTarget;

	private static Logger logger = LoggerFactory.getLogger(ComputerController.class);

	Page<ComputerDTO> computerPage;
	boolean isGoingBack;
	ObjectMapper objectMapper;
	User user;

	private String authorizationHeader = "Authorization";
	private String authorizationToken;

	/**
	 * Default constructor.
	 */
	ComputerController(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
		this.client = ClientBuilder.newClient().property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		this.webTarget = client.target("http://localhost:8080/api/v1/computers");
		this.isGoingBack = false;
		this.objectMapper = new ObjectMapper();
	}

	public void setUser(User user) {
		this.user = user;
		this.authorizationToken = String.format("Bearer %s", user.getToken());
	}

	/**
	 * Fetch computer list and fill controller field.
	 *
	 * @throws ItemNotFoundException
	 */
	public void refreshComputerPage() throws ItemNotFoundException {
		try {
			String result = this.webTarget.request().header(authorizationHeader, authorizationToken).get(String.class);
			List<ComputerDTO> listComputers = this.objectMapper.readValue(result,
					new TypeReference<List<ComputerDTO>>() {
					});
			this.computerPage = new Page<>(listComputers);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(e.getMessage());
		}
	}

	public Page<ComputerDTO> getComputerPage() {
		return this.computerPage;
	}

	/**
	 * Choose if we select the next or previous page.
	 *
	 * @param action Action to be processed.
	 * @return true if it is a predictable action (next, previous, back), else
	 *         false.
	 */
	public boolean selectAction(String action) {
		Utils.ChoiceActionPage choiceActionPage = Utils.stringToEnum(ChoiceActionPage.class, action);
		boolean badAction = true;
		switch (choiceActionPage) {
		case NEXT:
			this.computerPage.next();
			break;
		case PREVIOUS:
			this.computerPage.previous();
			break;
		case BACK:
			this.isGoingBack = true;
			break;
		default:
			badAction = false;
			break;
		}
		return badAction;
	}

	public boolean isGoingBack() {
		return isGoingBack;
	}

	/**
	 *
	 * @param id The id of the wanted computer.
	 * @return The computer that we want.
	 * @throws ItemNotFoundException
	 */
	public ComputerDTO getComputerById(int id) throws ItemNotFoundException {
		try {
			String result = this.webTarget.path("/" + id).request(MediaType.APPLICATION_JSON)
					.header(authorizationHeader, authorizationToken).get(String.class);
			return this.objectMapper.readValue(result, ComputerDTO.class);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ItemNotFoundException(e.getMessage());
		}
	}

	/**
	 *
	 * @param name         Name of the computer.
	 * @param introduced   Date (in string) of its introduction.
	 * @param discontinued Date (in string) when it will be discontinued.
	 * @param companyName  Name of the company.
	 * @return true if computer has been created else false.
	 * @throws ItemBadCreatedException
	 * @throws ComputerException
	 */
	public void createComputer(String name, String introduced, String discontinued, String companyId)
			throws ItemBadCreatedException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(name);
		computerDTO.setIntroducedDate(introduced);
		computerDTO.setDiscontinuedDate(discontinued);
		int companyIdInt = 0;
		try {
			companyIdInt = Integer.parseInt(companyId);
		} catch (NumberFormatException e) {
			companyIdInt = 0;
		}
		computerDTO.setCompanyId(companyIdInt);

		try {
			String json = this.objectMapper.writeValueAsString(computerDTO);
			if (this.webTarget.request().header(authorizationHeader, authorizationToken)
					.post(Entity.entity(json, MediaType.APPLICATION_JSON)).getStatus() != 200) {
				throw new ItemBadCreatedException("computerController");
			}
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			throw new ItemBadCreatedException(e.getMessage());
		}
	}

	public void updateComputer(int id, String name, String introduced, String discontinued, int companyId)
			throws ItemNotUpdatedException, ItemNotFoundException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(id);
		computerDTO.setName(name);
		computerDTO.setIntroducedDate(introduced);
		computerDTO.setDiscontinuedDate(discontinued);
		computerDTO.setCompanyId(companyId);
		try {
			String json = this.objectMapper.writeValueAsString(computerDTO);
			if (this.webTarget.path("/" + id).request().header(authorizationHeader, authorizationToken)
					.build("PATCH", Entity.entity(json, MediaType.APPLICATION_JSON)).invoke().getStatus() != 200) {
				throw new ItemNotUpdatedException("computerController");
			}
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			throw new ItemNotUpdatedException(e.getMessage());
		}
	}

	/**
	 *
	 * @param computer Computer to be deleted.
	 * @return true if computer has been deleted else false.
	 * @throws ItemNotFoundException
	 * @throws ItemNotDeletedException
	 */
	public void deleteComputer(int id) throws ItemNotDeletedException {
		if (this.webTarget.path("/" + id).request().header(authorizationHeader, authorizationToken).delete()
				.getStatus() != 200) {
			throw new ItemNotDeletedException("computerController");
		}
	}
}
