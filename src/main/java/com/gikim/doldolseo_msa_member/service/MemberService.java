package com.gikim.doldolseo_msa_member.service;

import com.gikim.doldolseo_msa_member.dto.MemberDTO;

public interface MemberService {
    MemberDTO registMember(MemberDTO dto);

    MemberDTO getMember(String id);

    void deleteMember(String id);

    void updateMember(String id, MemberDTO dto);

    boolean checkMemberId(String id);

    boolean checkMemberNickName(String nickName);
}