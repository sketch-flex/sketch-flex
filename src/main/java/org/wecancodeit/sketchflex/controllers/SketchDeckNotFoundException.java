package org.wecancodeit.sketchflex.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SketchDeckNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7731571911415429108L;

}
