package com.crossover.techtrial.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.techtrial.constants.Constants;
import com.crossover.techtrial.exceptions.BookAlreadyIssuedException;
import com.crossover.techtrial.exceptions.BookLimitException;
import com.crossover.techtrial.exceptions.BookNotIssuedException;
import com.crossover.techtrial.exceptions.EntityNotFoundException;
import com.crossover.techtrial.model.Book;
import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.model.Transaction;
import com.crossover.techtrial.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	 @Autowired TransactionRepository transactionRepository;
	  
	 @Autowired BookService bookService;
	  
	 @Autowired MemberService memberService;

	@Override
	public Transaction issueBookToMember(Long bookId, Long memberId) throws EntityNotFoundException, BookAlreadyIssuedException, BookLimitException {
		
		Book book = bookService.findById(bookId);
		Member member = memberService.findById(memberId); 
		
		if( book.getIsIssued()) {
			throw new BookAlreadyIssuedException("Book id " + bookId +" looking for has been issue to another member");
		}
		else if (member.getBookCount() >= Constants.MAX_NUMBER_OF_BOOK_ALLOWED)
		{
			throw new BookLimitException("Member id: "+ memberId + "has reached maximum  number of books issued. Please return a book to issue this book.");
		}
		else
		{
			Transaction transaction = new Transaction();
			book.setIsIssued(true);
			member.setBookCount(member.getBookCount()+1);
			transaction.setBook(book);
			transaction.setMember(member);
			transaction.setDateOfIssue(LocalDateTime.now());    
			return transactionRepository.save(transaction);
			 
		}
	}
	
	@Override
	  public Transaction findById(Long transactionId) throws EntityNotFoundException{
		  return transactionRepository.findById(transactionId)
		            .orElseThrow(() -> new EntityNotFoundException("Could not find transaction with id: " + transactionId));
	  }

	@Override
	public Transaction returnBook(Long transactionId) throws EntityNotFoundException, BookNotIssuedException {
		Transaction transaction = findById(transactionId);
		transaction.setDateOfReturn(LocalDateTime.now());
		long bookId = transaction.getBook().getId();
		Book book = bookService.findById(bookId);
		
		if(!book.getIsIssued() || null == transaction.getDateOfReturn()) {
			throw new BookNotIssuedException("Book id " + book.getId() +" which you are trying to return has not been issued");
		}
		book.setIsIssued(false);
		transaction.getMember().setBookCount(transaction.getMember().getBookCount()-1);
		return transactionRepository.save(transaction);
	}
}
