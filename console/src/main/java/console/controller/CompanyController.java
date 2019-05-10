package console.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import binding.dto.CompanyDTO;
import core.model.Page;
import core.model.User;
import core.util.Utils;
import core.util.Utils.ChoiceActionPage;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;

@Component
public class CompanyController {

	private Client client;
	private WebTarget webTarget;

	private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

	Page<CompanyDTO> companyPage;
	boolean isGoingBack;
	User user;

	CompanyController() {
		this.client = ClientBuilder.newClient().property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		this.webTarget = this.client.target("http://localhost:8080/api/v1/company");
		this.isGoingBack = false;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Fetch company list and fill controller field.
	 *
	 * @throws ItemNotFoundException
	 */
	public void refreshCompanyPage() throws ItemNotFoundException {
		String result = this.webTarget.request().header("Authorization", "Bearer " + user.getToken()).get(String.class);
		ObjectMapper obj = new ObjectMapper();
		List<CompanyDTO> listCompanies;
		try {
			listCompanies = obj.readValue(result, new TypeReference<List<CompanyDTO>>() {
			});
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

		this.companyPage = new Page<>(listCompanies);
	}

	public Page<CompanyDTO> getCompanyPage() {
		return this.companyPage;
	}

	public CompanyDTO getCompanyById(int id) throws ItemNotFoundException {
		try {
			String result = this.webTarget.path("/" + id).request().header("Authorization", "Bearer " + user.getToken())
					.get(String.class);
			ObjectMapper obj = new ObjectMapper();
			return obj.readValue(result, CompanyDTO.class);
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

	public void deleteCompany(int id) throws ItemNotDeletedException {
		if (this.webTarget.path("/" + id).request().header("Authorization", "Bearer " + user.getToken())
				.delete().getStatus() != 200) {
			throw new ItemNotDeletedException("companyController");
		}
	}

	/**
	 * Choose if we select the next or previous page.
	 *
	 * @param action Action processing.
	 * @return true if the choice corresponds to next/previous/back else false.
	 */
	public boolean selectAction(String action) {
		Utils.ChoiceActionPage choiceActionPage = Utils.stringToEnum(ChoiceActionPage.class, action);
		boolean badAction = true;
		switch (choiceActionPage) {
		case NEXT:
			this.companyPage.next();
			break;
		case PREVIOUS:
			this.companyPage.previous();
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
}
