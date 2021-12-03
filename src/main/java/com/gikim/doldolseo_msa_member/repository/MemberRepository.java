package com.gikim.doldolseo_msa_member.repository;

import com.gikim.doldolseo_msa_member.dolmain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String id);
    Optional<Member> findByNickname(String nickname);

    @Transactional
    void deleteById(String id);
}
