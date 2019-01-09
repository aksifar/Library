/**
 * 
 */
package com.crossover.techtrial.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.model.Book;
import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.model.Transaction;
import com.crossover.techtrial.repositories.BookRepository;
import com.crossover.techtrial.repositories.MemberRepository;
import com.crossover.techtrial.repositories.TransactionRepository;

/**
 * @author ankit ranjan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {
  
  @Mock private MemberController transactionController;
  @Mock private BookController bookController;
  @Mock private MemberController memberController;
  @Autowired private TestRestTemplate template;
  @Autowired TransactionRepository transactionRepository;
  @Autowired MemberRepository memberRepository;
  @Autowired BookRepository bookRepository;
  
  private Long bookId, memberId;
  private ResponseEntity<Transaction> response;  
  private MockMvc mockTransactionController, mockBookController, mockMemberController;
  
  @Before
  public void setup() throws Exception {
    mockTransactionController = MockMvcBuilders.standaloneSetup(transactionController).build();
    mockBookController = MockMvcBuilders.standaloneSetup(bookController).build();
    mockMemberController = MockMvcBuilders.standaloneSetup(memberController).build();
    
    HttpEntity<Object> book = getHttpEntity( "{\"title\": \"Book 1\", \"isIssued\": false }");
    ResponseEntity<Book> responseBook = template.postForEntity("/api/book", book, Book.class);
    bookId = responseBook.getBody().getId();
    
    HttpEntity<Object> member = getHttpEntity("{\"name\": \"Memeber01\", \"email\": \"member001@gmail.com\"," 
                + " \"membershipStatus\": \"ACTIVE\",\"membershipStartDate\":\"2019-01-06T00:00:00\" }");
        
   ResponseEntity<Member> responseMember = template.postForEntity("/api/member", member, Member.class);
   memberId =  responseMember.getBody().getId();
  }
  
  @Test
  public void issueBookToMemberSuccessful() throws Exception {
    HttpEntity<Object> transaction = getHttpEntity( "{\"bookId\": " + bookId +", \"memberId\": "+ memberId+" }");
    response = template.postForEntity("/api/transaction", transaction, Transaction.class);
   
	Assert.assertEquals(bookId, response.getBody().getBook().getId());
	Assert.assertEquals(memberId, response.getBody().getMember().getId());
	Assert.assertEquals(200,response.getStatusCode().value());
  }
  
  @Test
  public void issueBookToMemberFailure() throws Exception {
    HttpEntity<Object> transaction = getHttpEntity("{\"bookId\": " + "-1" +", \"memberId\": "+ memberId+" }");
    ResponseEntity<Transaction> response = template.postForEntity("/api/transaction", transaction, Transaction.class);
	
	Assert.assertEquals(404,response.getStatusCode().value());
  }
  
  @Test
  public void issueAlreadyIssuedBookToMemberForbidden() throws Exception {
	//Create a transaction  
	  HttpEntity<Object> transaction = getHttpEntity( "{\"bookId\": " + bookId +", \"memberId\": "+ memberId +" }");
	    template.postForEntity("/api/transaction", transaction, Transaction.class);
	    
	    //create another member
	    HttpEntity<Object> member2 = getHttpEntity("{\"name\": \"Memeber02\", \"email\": \"member002@gmail.com\"," 
                + " \"membershipStatus\": \"ACTIVE\",\"membershipStartDate\":\"2019-01-06T00:00:00\" }");
        
	   ResponseEntity<Member> responseMember = template.postForEntity("/api/member", member2, Member.class);
	   Long member2Id =  responseMember.getBody().getId();
	  
	   //Try Assigning same book to member2
	   transaction = getHttpEntity( "{\"bookId\": " + bookId +", \"memberId\": "+ member2Id +" }");
	   response =  template.postForEntity("/api/transaction", transaction, Transaction.class); 
		Assert.assertEquals(403,response.getStatusCode().value());
	  }
  
  @After
  public void cleanUp() {
	//cleanup the user
	  if(null != response && null != response.getBody())
	  {
		  transactionRepository.deleteById(response.getBody().getId());
	  }
	  memberRepository.deleteById(memberId);
	  bookRepository.deleteById(bookId);
	  
  }
  
  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
