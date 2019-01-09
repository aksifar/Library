/**
 * 
 */
package com.crossover.techtrial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.techtrial.exceptions.EntityNotFoundException;
import com.crossover.techtrial.model.Book;
import com.crossover.techtrial.repositories.BookRepository;
import com.crossover.techtrial.repositories.TransactionRepository;

/**
 * @author crossover
 *
 */
@Service
public class BookServiceImpl implements BookService{

  @Autowired
  BookRepository bookRepository;
  
  @Autowired
  TransactionRepository transactionRepository;
  
  @Override
  public List<Book> getAll() {
    List<Book> bookList = new ArrayList<>();
    bookRepository.findAll().forEach(bookList::add);
    return bookList;
    
  }
  
  public Book save(Book p) {
    return bookRepository.save(p);
  }

  @Override
  public Book findById(Long bookId) throws EntityNotFoundException{
	  return bookRepository.findById(bookId)
	            .orElseThrow(() -> new EntityNotFoundException("Could not find book with id: " + bookId));
  }

}
