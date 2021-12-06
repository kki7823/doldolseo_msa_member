package com.gikim.doldolseo_msa_member.controller;

import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import com.gikim.doldolseo_msa_member.dto.MemberLoginRequest;
import com.gikim.doldolseo_msa_member.dto.MemberLoginResponse;
import com.gikim.doldolseo_msa_member.service.MemberAuthService;
import com.gikim.doldolseo_msa_member.service.MemberService;
import com.gikim.doldolseo_msa_member.utils.JwtTokenUtil;
import com.gikim.doldolseo_msa_member.utils.UploadProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberAuthService memberAuthService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UploadProfileUtil uploadProfileUtil;

    @PostMapping("/member")
    public ResponseEntity<MemberDTO> registerMember(MemberDTO dto, MultipartFile memberImgFile) throws IOException {
        String profileImg = "sample.png";
        if (!(memberImgFile.isEmpty())) {
            profileImg = uploadProfileUtil.uploadProfile(memberImgFile, dto);
        }
        dto.setMemberImg(profileImg);
        MemberDTO member = memberService.registMember(dto);

        System.out.println(member.toString());

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/member/login")
    @ResponseBody
    public ResponseEntity<?> loginMember(@RequestBody MemberLoginRequest request,
                                         HttpServletResponse response) throws Exception {
        System.out.println("MEMBER CONTROLLER");
        final MemberDTO dto = memberAuthService.authenticateMember(request.getId(), request.getPassword());
        final String token = jwtTokenUtil.generateToken(dto.getId());

        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);
//        jwtCookie.setSecure(true);
//        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(new MemberLoginResponse(token));
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable String id) {
        MemberDTO dto = memberService.getMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity<MemberDTO> updateMember(MemberDTO memberDTO, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<MemberDTO> deleteMember(MemberDTO memberDTO, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/test")
    public ResponseEntity<MemberDTO> testMember(String id) {
        MemberDTO dto = memberService.getMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
