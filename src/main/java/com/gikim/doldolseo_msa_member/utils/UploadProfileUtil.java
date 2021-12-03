package com.gikim.doldolseo_msa_member.utils;


import com.gikim.doldolseo_msa_member.dto.MemberDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadProfileUtil {
    private final Path ROOT_PATH;

    public UploadProfileUtil(String uploadPath) {
        ROOT_PATH = Paths.get(uploadPath);
    }

    public void makeProfileDir(){
        String path = ROOT_PATH.toString() + "/profile";
        File Directory = new File(path);
        if(!Directory.exists()){
            Directory.mkdirs();
        }
    }

    public String uploadProfile(MultipartFile profile, MemberDTO dto) throws IOException {
        makeProfileDir();
        String file = profile.getOriginalFilename();
        String fileName = dto.getId() + "." + file.substring(file.lastIndexOf(".") + 1);
        String imageDirPath = ROOT_PATH.toString() + "/profile";
        String filePath = imageDirPath + "/" + fileName;
        File dest = new File(filePath);
        profile.transferTo(dest);
        return fileName;
    }

}
