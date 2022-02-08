package com.gikim.doldolseo_msa_member.controller;

import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import com.gikim.doldolseo_msa_member.dto.MemberLoginDTO;
import com.gikim.doldolseo_msa_member.service.MemberAuthService;
import com.gikim.doldolseo_msa_member.service.MemberService;
import com.gikim.doldolseo_msa_member.utils.JwtTokenUtil;
import com.gikim.doldolseo_msa_member.utils.UploadProfileUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public ResponseEntity<MemberDTO> registerMember(MemberDTO dto,
                                                    @RequestParam(required = false) MultipartFile memberImgFile) throws IOException {
        String profileImg = "default_member.png";
        if (memberImgFile != null) {
            if (!memberImgFile.isEmpty()) {
                profileImg = uploadProfileUtil.uploadProfile(memberImgFile, dto);
            }
        }
        dto.setMemberImg(profileImg);
        MemberDTO member = memberService.registMember(dto);

        System.out.println(member.toString());
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/member/check")
    public ResponseEntity<Boolean> check(@RequestParam(required = false) String id,
                                         @RequestParam(required = false) String nickName) {
        Boolean isExist = null;

        if (id != null)
            isExist = memberService.checkMemberId(id);
        else if (nickName != null)
            isExist = memberService.checkMemberNickName(nickName);

        return ResponseEntity.status(HttpStatus.OK).body(isExist);
    }

    @PostMapping("/member/login")
    @ResponseBody
    public ResponseEntity<MemberDTO> loginMember(@RequestBody MemberLoginDTO loginDTO,
                                                 HttpServletResponse response) throws Exception {
        final MemberDTO memberDTO = memberAuthService.authenticateMember(loginDTO.getId(), loginDTO.getPassword());
        final String token = jwtTokenUtil.generateToken(memberDTO.getId());

        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);
//        jwtCookie.setSecure(true);
//        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(memberDTO);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable String id, HttpServletRequest request) {
        MemberDTO dto = memberService.getMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity<MemberDTO> updateMember(MemberDTO dto,
                                                  @RequestParam(required = false) MultipartFile memberImgFile,
                                                  @PathVariable String id) throws IOException {
        if (memberImgFile != null) {
            if (!memberImgFile.isEmpty()) {
                String profileImg = uploadProfileUtil.uploadProfile(memberImgFile, dto);
                dto.setMemberImg(profileImg);
            }
        }

        memberService.updateMember(id, dto);
        MemberDTO responseDTO = memberService.getMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable String id) {
        MemberDTO dto = memberService.getMember(id);
        if (dto.getMemberRole().equals("CREWLEADER")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 멤버는 크루장 입니다.");
        }

        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping(value = "/member/images/{imageFileName}"
            ,produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public @ResponseBody
    byte[] getMemberImages(@PathVariable String imageFileName) throws IOException {

        String imgPath = System.getProperty("user.dir")
                + "/src/main/resources/static/images/profile/"
                + imageFileName;
        InputStream in = new FileInputStream(imgPath);
        byte[] imageByteArr = IOUtils.toByteArray(in);
        in.close();
        return imageByteArr;
    }
}
