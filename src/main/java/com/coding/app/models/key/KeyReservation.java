package com.coding.app.models.key;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KeyReservation implements Serializable{

	@Column(name = "username")
	private String username;

	@Column(name = "carId")
	private Long carId;

}
