package com.coding.app.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
@Getter
@Setter
public class InvalidObjectException extends Exception {

	private HashMap<String, String> errors = new HashMap<String,String>();

	public InvalidObjectException(String msg, HashMap<String, String> errors) {
		super(msg);
		this.errors = errors;
	}
}