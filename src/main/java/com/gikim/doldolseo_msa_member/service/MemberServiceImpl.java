package com.gikim.doldolseo_msa_member.service;

import com.gikim.doldolseo_msa_member.domain.Member;
import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import com.gikim.doldolseo_msa_member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public MemberDTO registMember(MemberDTO dto) {
        String rawPassword = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        dto.setPassword(encodedPassword);
        dto.setJoinDate(LocalDateTime.now());
        dto.setMemberRole("USER");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = null;
        try {
            birth = formatter.parse(dto.getBirth());
        } catch (ParseException e) {
            System.out.println("MemberServiceImpl.registMember : " + e.getMessage());
        }

        Member memberEntity = modelMapper.map(dto, Member.class);
        Member member = repository.save(memberEntity);
        member.setBirth(birth);

        return modelMapper.map(member, MemberDTO.class);
    }

    @Override
    public boolean checkMemberId(String id) {
        return repository.existsById(id);
    }

    @Override
    public boolean checkMemberNickName(String nickName) {
        return repository.existsByNickname(nickName);
    }

    @Override
    public MemberDTO getMember(String id) throws UsernameNotFoundException {
        Member member = repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id));
        return modelMapper.map(member, MemberDTO.class);
    }

    @Override
    public String getMemberNickname(String id) throws UsernameNotFoundException {
        return (String) repository.findNickNameById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMember(String id, MemberDTO dto) {
        System.out.println(dto.toString());
        Member member = repository
                .findById(id).orElseThrow(() -> new UsernameNotFoundException(id));

        if (dto.getMemberImg() != null)
            member.setMemberImg(dto.getMemberImg());


        if (dto.getEmail() != null)
            member.setEmail(dto.getEmail());

        member.setPhone(dto.getPhone());

        if (dto.getPassword() != null) {
            String rawPassword = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            member.setPassword(encodedPassword);
        }

        member.setGender(dto.getGender());
        System.out.println("updateMember is done.");
    }

    @Transactional
    @Override
    public void updateUserToCrewLeader(String id){
        Member member = repository
                .findById(id).orElseThrow(() -> new UsernameNotFoundException(id));
        member.setMemberRole("CREWLEADER");
    }

    @Transactional
    @Override
    public void updateCrewLeaderToUser(String id){
        Member member = repository
                .findById(id).orElseThrow(() -> new UsernameNotFoundException(id));
        member.setMemberRole("USER");
    }

    @Override
    public void deleteMember(String id) {
        repository.deleteById(id);
        System.out.println("member id " + id + " is deleted.");
    }

}
