package com.gikim.doldolseo_msa_member.service;

import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService {
    MemberDTO registMember(MemberDTO dto);

    MemberDTO getMember(String id);

    String getMemberNickname(String id) throws UsernameNotFoundException;

    void deleteMember(String id);

    void updateMember(String id, MemberDTO dto);

    void updateUserToCrewLeader(String id);

    void updateCrewLeaderToUser(String id);

    boolean checkMemberId(String id);

    boolean checkMemberNickName(String nickName);
}