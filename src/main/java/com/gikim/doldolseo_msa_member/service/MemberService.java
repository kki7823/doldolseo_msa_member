package com.gikim.doldolseo_msa_member.service;

import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService {
    public MemberDTO registMember(MemberDTO dto);
    public MemberDTO authenticateMember(String id, String password);
    public UserDetails getMemberWithAuthorities(String id);
    public MemberDTO getMember(String id);
    public MemberDTO updateMember(MemberDTO dto);
    public MemberDTO deleteMember(MemberDTO dto);
}