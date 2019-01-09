/**
 * 
 */
package com.crossover.techtrial.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.techtrial.exceptions.EntityNotFoundException;
import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.repositories.MemberRepository;

/**
 * @author crossover
 *
 */
@Service
public class MemberServiceImpl implements MemberService{

  @Autowired
  MemberRepository memberRepository;
  
  public Member save(Member member) {
    return memberRepository.save(member);
  }
  
  public Member findById(Long memberId) throws EntityNotFoundException {
	  
	  return memberRepository.findById(memberId)
      .orElseThrow(() -> new EntityNotFoundException("Could not find member with id: " + memberId));
  }
  
  public List<Member> findAll() {
    return memberRepository.findAll();
  }

	@Override
	public List<Member> getTopMembers(LocalDateTime startTime, LocalDateTime endTime) throws EntityNotFoundException {
		List<Long> memberIdList =  memberRepository.findTopMembers(startTime, endTime);
		List<Member> memberList = new ArrayList<>();
		Long id;
		for(int i=0; i<memberIdList.size(); i++)
		{
			//MySQL return BigInteger instead of Long
			Object idCheck =  memberIdList.get(i);
			if(idCheck instanceof BigInteger)
			{
				id =((BigInteger) idCheck).longValueExact();
				memberList.add( findById(id) );
			}
			else if(idCheck instanceof Long)
			{
				id =(Long) idCheck;
				memberList.add( findById(id) );
			}
		}
		return memberList;
	}
}
