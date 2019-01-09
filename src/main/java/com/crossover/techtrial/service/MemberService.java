/**
 * 
 */
package com.crossover.techtrial.service;

import java.time.LocalDateTime;
import java.util.List;

import com.crossover.techtrial.exceptions.EntityNotFoundException;
import com.crossover.techtrial.model.Member;

/**
 * RideService for rides.
 * @author crossover
 *
 */
public interface MemberService {
  
  public Member save(Member member);
  
  public Member findById(Long memberId) throws EntityNotFoundException;
  
  public List<Member> findAll();

  public List<Member> getTopMembers(LocalDateTime startTime, LocalDateTime endTime) throws EntityNotFoundException;

}

