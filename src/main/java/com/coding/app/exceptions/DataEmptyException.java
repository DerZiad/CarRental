package com.coding.app.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class DataEmptyException extends Exception{

	public DataEmptyException(String msg) {
		super(msg);
	}
}