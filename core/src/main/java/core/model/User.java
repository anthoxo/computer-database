package core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 333421094248290492L;

	@Transient
	private String token = "";

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "username")
	private String username;

	@Column(name = "role")
	private String role;

	public int getId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
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

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		ToStringBuilder ts = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		ts.append("id", id);
		ts.append("email", email);
		ts.append("password", "HIDDEN");
		ts.append("username", username);
		ts.append("role", role);
		ts.append("token", token);
		return ts.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		} else {
			User user = (User) obj;
			return this.id == user.id && this.email.equals(user.email) && this.password.equals(user.password)
					&& this.username.equals(user.username) && this.role.equals(user.role);
		}
	}

	@Override
	public int hashCode() {
		return this.getId() + this.getEmail().hashCode() + this.getPassword().hashCode()
				+ this.getUsername().hashCode() + this.getRole().hashCode();
	}

	public static class Builder {
		private int id;
		private String email;
		private String password;
		private String username;
		private String role;
		private String token;

		public Builder() {
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder withHashPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder withRole(String role) {
			this.role = role;
			return this;
		}

		public Builder withToken(String token) {
			this.token = token;
			return this;
		}


		public User build() {
			User user = new User();
			user.setId(id);
			user.setEmail(email);
			user.setPassword(password);
			user.setUsername(username);
			user.setRole(role);
			user.setToken(token);
			return user;
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> l = new ArrayList<SimpleGrantedAuthority>();
		l.add(new SimpleGrantedAuthority("ROLE_USER"));
		if ("ADMIN".equals(role)) {
			l.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return l;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
