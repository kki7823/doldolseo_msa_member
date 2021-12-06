package com.gikim.doldolseo_msa_member.service;

import com.gikim.doldolseo_msa_member.dto.MemberDTO;

public interface MemberService {
    public MemberDTO registMember(MemberDTO dto);
    public MemberDTO getMember(String id);
    public MemberDTO updateMember(MemberDTO dto);
    public MemberDTO deleteMember(MemberDTO dto);
}