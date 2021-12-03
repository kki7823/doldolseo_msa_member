package com.gikim.doldolseo_msa_member.controller;

import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import com.gikim.doldolseo_msa_member.dto.MemberLoginRequest;
import com.gikim.doldolseo_msa_member.dto.MemberLoginResponse;
import com.gikim.doldolseo_msa_member.service.MemberService;
import com.gikim.doldolseo_msa_member.utils.JwtTokenUtil;
import com.gikim.doldolseo_msa_member.utils.UploadProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    @Autowired
    private MemberService service;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UploadProfileUtil uploadProfileUtil;

    @PostMapping("/member")
    public ResponseEntity<MemberDTO> memberRegiter(MemberDTO dto, MultipartFile memberImgFile) throws IOException {
        String profileImg = "sample.png";
        if (!(memberImgFile.isEmpty())) {
            profileImg = uploadProfileUtil.uploadProfile(memberImgFile, dto);
        }
        dto.setMemberImg(profileImg);
        MemberDTO member = service.registMember(dto);

        System.out.println(member.toString());

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/member/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody MemberLoginRequest request) throws Exception{
        final MemberDTO dto = service.authenticateMember(request.getId(), request.getPassword());

        final String token = jwtTokenUtil.generateToken(dto.getId());
            return ResponseEntity.ok(new MemberLoginResponse(token));
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<MemberDTO> getMember(MemberDTO memberDTO, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity<MemberDTO> updateMember(MemberDTO memberDTO, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<MemberDTO> deleteMember(MemberDTO memberDTO, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
