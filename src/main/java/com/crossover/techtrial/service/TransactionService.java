package com.crossover.techtrial.service;

import com.crossover.techtrial.exceptions.BookAlreadyIssuedException;
import com.crossover.techtrial.exceptions.BookLimitException;
import com.crossover.techtrial.exceptions.BookNotIssuedException;
import com.crossover.techtrial.exceptions.EntityNotFoundException;
import com.crossover.techtrial.model.Transaction;

public interface TransactionService {

	Transaction issueBookToMember(Long bookId, Long memberId) throws EntityNotFoundException, BookAlreadyIssuedException, BookLimitException;

	Transaction returnBook(Long transactionId) throws EntityNotFoundException, BookNotIssuedException;

	Transaction findById(Long transactionId) throws EntityNotFoundException;

}
