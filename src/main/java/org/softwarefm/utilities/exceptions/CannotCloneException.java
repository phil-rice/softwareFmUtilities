package org.softwarefm.utilities.exceptions;

public class CannotCloneException extends RuntimeException {

	public CannotCloneException(Class<? extends Object> class1) {
		super(class1.getName());
	}

	public CannotCloneException(Class<? extends Object> class1 , Throwable arg1) {
		super(class1.getName(), arg1);
	}

}
