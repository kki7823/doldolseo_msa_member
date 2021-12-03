package com.gikim.doldolseo_msa_member.dolmain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "MEMBER_TBL")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    private String id;

    private String password;
    private String name;
    private String nickname;

    @Column(name = "MEMBER_IMG")
    private String memberImg;

    private Date birth;
    private String gender;
    private String email;
    private String phone;

    @Column(name = "JOIN_DATE")
    private LocalDateTime joinDate;

    private Boolean isCrleader;
}
