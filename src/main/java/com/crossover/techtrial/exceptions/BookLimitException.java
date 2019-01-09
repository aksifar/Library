package com.crossover.techtrial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Member has reached maximum  number of books issued. Please return a book to issue this book.")
public class BookLimitException extends Exception
{
	private static final long serialVersionUID = 3048811182524130729L;

	public BookLimitException(String message)
    {
        super(message);
    }

}
