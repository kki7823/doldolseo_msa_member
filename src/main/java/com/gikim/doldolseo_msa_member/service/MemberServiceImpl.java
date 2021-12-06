package com.gikim.doldolseo_msa_member.service;

import com.gikim.doldolseo_msa_member.dolmain.Member;
import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import com.gikim.doldolseo_msa_member.repository.MemberRepository;
import com.gikim.doldolseo_msa_member.jwt.MemberRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    public MemberDTO getMember(String id) throws UsernameNotFoundException {
        Member member = repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id));
        return modelMapper.map(member, MemberDTO.class);
    }

    @Override
    public MemberDTO updateMember(MemberDTO dto) {
        return null;
    }

    @Override
    public MemberDTO deleteMember(MemberDTO dto) {
        return null;
    }
}
