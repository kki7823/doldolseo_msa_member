package com.gikim.doldolseo_msa_member.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    USER("ROLE_USER"),
    CREWLEADER("ROLE_CREW_LEADER");

    private String vlaue;
}
