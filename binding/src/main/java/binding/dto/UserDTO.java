package binding.dto;

public class UserDTO {

	private int id;
	private String email;
	private String password;
	private String username;
	private String role;
	private String token;

	public int getId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public String getRole() {
		return this.role;
	}

	public String getToken() {
		return this.token;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String hashPassword) {
		this.password = hashPassword;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
