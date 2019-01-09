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
import com.crossover.techtrial.repositories.BookRepository;

/**
 * @author ankit ranjan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
  
  MockMvc mockBookController;
  
  @Mock
  private BookController bookController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  BookRepository bookRepository;
  
  private HttpEntity<Object> book;
  private ResponseEntity<Book> response;
  private long bookId;
  
  @Before
  public void setup() throws Exception {
    mockBookController = MockMvcBuilders.standaloneSetup(bookController).build();
    book = getHttpEntity("{\"title\": \"Book1\", \"isIssued\": false }");
       
    response = template.postForEntity("/api/book", book, Book.class);
    bookId = response.getBody().getId();
  }
  
  @Test
  public void testSaveBook() throws Exception {
    
    Assert.assertEquals("Book1", response.getBody().getTitle());
    Assert.assertEquals(false, response.getBody().getIsIssued());
    Assert.assertEquals(200,response.getStatusCode().value());
  }
  
  @Test
  public void testGetBookByIdSuccessfull() throws Exception {
	response = template.getForEntity("/api/book/" +bookId, Book.class);
    Assert.assertEquals("Book1", response.getBody().getTitle());
    Assert.assertEquals(false, response.getBody().getIsIssued());
    Assert.assertEquals(200,response.getStatusCode().value());
   
  }
  
  @Test
  public void testGetBookByIdFailure() throws Exception {
	response = template.getForEntity("/api/book/" +"-1", Book.class);
    Assert.assertEquals(400, response.getStatusCode().value());
   
  }

  @After
  public void cleanUp() throws Exception
  {
	  //cleanup the user
	  bookRepository.deleteById(bookId);
  }
  
  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
