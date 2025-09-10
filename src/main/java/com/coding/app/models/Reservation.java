package com.coding.app.models;

import java.io.Serializable;
import java.sql.Date;

import com.coding.app.models.key.KeyReservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reservations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation implements Serializable{

	@EmbeddedId
	private KeyReservation id;

	@Column
	boolean isConfirmed = false;
	
	private long dateCreation = System.currentTimeMillis();
	
	private Date startDay;

    private Date endDay;
			
	/**
	 * Relations
	 * */
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
	@MapsId("idUser")
	private User user;
	
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
	@MapsId("idVoiture")
	@JsonIgnore
	private Car car;
			
	public boolean isDead() {
		return false;
	}
}
