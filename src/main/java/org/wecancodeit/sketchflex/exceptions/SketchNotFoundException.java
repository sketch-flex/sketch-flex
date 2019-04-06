package org.wecancodeit.sketchflex.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class SketchNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
