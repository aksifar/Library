package com.crossover.techtrial.controller.mapper;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.crossover.techtrial.dto.TopMemberDTO;
import com.crossover.techtrial.model.Member;

public class MemberMapper {

	private static TopMemberDTO makeTopMember(Member member)
    {
    	TopMemberDTO topMember = new TopMemberDTO();
    	
    	topMember.setMemberId(member.getId());
    	topMember.setName(member.getName());
    	topMember.setEmail(member.getEmail());
    	topMember.setBookCount(member.getBookCount());
        return topMember;
    }

	public static List<TopMemberDTO> makeTopMemberList(Collection<Member> members)
    {
        return members.stream()
            .map(MemberMapper::makeTopMember)
            .collect(Collectors.toList());
    }
}
