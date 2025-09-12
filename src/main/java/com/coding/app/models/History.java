package com.coding.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "histories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String action;
	
	private Date date = new Date(System.currentTimeMillis());
	
	public History(String action) {
		this.action = action;
	}
	
}
