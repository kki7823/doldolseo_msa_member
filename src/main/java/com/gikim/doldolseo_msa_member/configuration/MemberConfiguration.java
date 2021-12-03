package com.gikim.doldolseo_msa_member.configuration;

import com.gikim.doldolseo_msa_member.utils.UploadProfileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "uploadPath")
    public String uploadPath() {
        return System.getProperty("user.dir")+"/src/main/resources/static/images";
    }

    @Bean
    public UploadProfileUtil uploadFileUtil(){
        return new UploadProfileUtil(uploadPath());
    }
}
