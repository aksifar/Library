package com.crossover.techtrial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Book you are trying to return has nnot been issued")
public class BookNotIssuedException extends Exception
{

	private static final long serialVersionUID = 3263345561884160863L;

	public BookNotIssuedException(String message)
    {
        super(message);
    }

}
