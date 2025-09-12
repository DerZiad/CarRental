package com.coding.app.models;

import com.coding.app.models.enums.ServerRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Serializable, Comparable<User> {

    @Id
	@Column(name = "username", unique = true, length = 50, nullable = false)
	@NotNull(message = "Username must not be null")
	@Pattern(regexp = ".*\\..*", message = "Username must contain a dot (.)")
	private String username;

	@Column(name = "password", length = 100, nullable = false)
	@Length(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
	@NotEmpty(message = "Password must not be empty")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}|\\[\\]:\";'<>?,./])[A-Za-z\\d!@#$%^&*()_+\\-={}|\\[\\]:\";'<>?,./]{8,}$",
		message = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character"
	)
	@JsonIgnore
	private String password;

	@Email(message = "Email must be valid")
	@NotNull(message = "Email must not be null")
	private String email;
	
	private String roles = "";
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VerificationCode> verificationCodes = new ArrayList<>();

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	private boolean validated = false;
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "user",targetEntity = Reservation.class)
	private List<Reservation> reservations = new ArrayList<Reservation>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles.split(";")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		return authorities;
	}

	public boolean isEmailVerified() {
		return this.validated;
	}

	public boolean isAdmin() {
		return roles.contains(ServerRole.ADMIN.name());
	}

	public void addRole(ServerRole role) {
		roles = roles + role.getRole() + ";";
	}

	@Override
	public int compareTo(User o) {
		return username.compareTo(o.getUsername());
	}

}
