/**
 * 
 */
package com.crossover.techtrial.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.techtrial.exceptions.BookAlreadyIssuedException;
import com.crossover.techtrial.exceptions.BookLimitException;
import com.crossover.techtrial.exceptions.BookNotIssuedException;
import com.crossover.techtrial.exceptions.EntityNotFoundException;
import com.crossover.techtrial.model.Transaction;
import com.crossover.techtrial.service.TransactionService;

/**
 * @author ankit ranjan
 *
 */
@RestController
public class TransactionController {
  
  @Autowired TransactionService transactionService;
  
  /*
   * PLEASE DO NOT CHANGE SIGNATURE OR METHOD TYPE OF END POINTS
   * Example Post Request :  { "bookId":1,"memberId":33 }
   */
  @PostMapping(path = "/api/transaction")
  public ResponseEntity<Transaction> issueBookToMember(@RequestBody Map<String, Long> params) {
	
	Transaction transaction;
    Long bookId = params.get("bookId");
    Long memberId = params.get("memberId");
    
    try 
    {
    	transaction = transactionService.issueBookToMember(bookId, memberId);
	} 
    catch (EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	} 
    catch (BookAlreadyIssuedException | BookLimitException e) {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
    return ResponseEntity.ok().body(transaction);
  }
  
  
  /*
   * PLEASE DO NOT CHANGE SIGNATURE OR METHOD TYPE OF END POINTS
   */
  @PatchMapping(path= "/api/transaction/{transaction-id}/return")
  public ResponseEntity<Transaction> returnBookTransaction(@PathVariable(name="transaction-id") Long transactionId) throws EntityNotFoundException{
    Transaction transaction;
	try 
	{
		transaction = transactionService.returnBook(transactionId);
	} 
	catch (BookNotIssuedException e) 
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
    return ResponseEntity.ok().body(transaction);
  }
  
}
