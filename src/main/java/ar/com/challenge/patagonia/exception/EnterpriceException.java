package ar.com.challenge.patagonia.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class EnterpriceException extends RuntimeException {

	private static final long serialVersionUID = 4717721573466018966L;
	
	private HttpStatus httpStatus;
	
	public EnterpriceException(HttpStatus httpStatus, String message) {
		super(message);
		
		this.httpStatus = httpStatus;
	}

}
