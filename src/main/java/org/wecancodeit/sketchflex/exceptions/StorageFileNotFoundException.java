package org.wecancodeit.sketchflex.exceptions;

import java.net.MalformedURLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason = "Image file Not Found")
public class StorageFileNotFoundException extends Exception {

	

	public StorageFileNotFoundException(String string, MalformedURLException e) {
		// TODO Auto-generated constructor stub
	}
	
	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
