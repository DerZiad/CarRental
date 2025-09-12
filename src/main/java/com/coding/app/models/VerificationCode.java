package com.coding.app.models;

import com.coding.app.models.enums.EmailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="verifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

	private final static long EXPIRATION_DELAY = 6 * 60 * 1000;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String code;
	
	@Enumerated(EnumType.STRING)
	private EmailType type;
	
	@ManyToOne
	@JoinColumn(name = "user_username")
	private User user;
	
	private String ip;
	
	private long date = System.currentTimeMillis();

	public boolean isDead() {
		return System.currentTimeMillis() >= (date + EXPIRATION_DELAY);
	}
}
