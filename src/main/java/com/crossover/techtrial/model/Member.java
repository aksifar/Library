/**
 * 
 */
package com.crossover.techtrial.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author ankit ranjan
 *
 */
@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(name = "member_email", columnNames = {"email"}))
public class Member implements Serializable{
  
  private static final long serialVersionUID = 9045098179799205444L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  @Size(min = 2, max = 100,  message = "Name must be between 2 and 100 characters")
  @Pattern(regexp  ="^[a-zA-Z][a-zA-Z 0-9.,$;]+$")
  private String name;

  @Size(max = 100)
  @Column(name = "email", unique=true)
//  @Email(message = "Email should be valid")
  private String email;
  
  @Enumerated(EnumType.STRING)
  private MembershipStatus membershipStatus;
  
  @Column(name = "membership_start_date")
  private LocalDateTime membershipStartDate = LocalDateTime.now();
  
  @Column(name ="book_count")
  @Max(value = 5)
  private int bookCount = 0; 

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public MembershipStatus getMembershipStatus() {
    return membershipStatus;
  }

  public void setMembershipStatus(MembershipStatus membershipStatus) {
    this.membershipStatus = membershipStatus;
  }

  public LocalDateTime getMembershipStartDate() {
    return membershipStartDate;
  }

  public void setMembershipStartDate(LocalDateTime membershipStartDate) {
    this.membershipStartDate = membershipStartDate;
  }

  public int getBookCount() {
	return bookCount;
  }

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Member other = (Member) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Member [id=" + id + ", name=" + name + ", email=" + email + "]";
  }
}
