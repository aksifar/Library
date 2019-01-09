package com.crossover.techtrial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Book you're looking for has been issue to another member")
public class BookAlreadyIssuedException extends Exception
{

	private static final long serialVersionUID = -7576163799151409630L;

	public BookAlreadyIssuedException(String message)
    {
        super(message);
    }

}
