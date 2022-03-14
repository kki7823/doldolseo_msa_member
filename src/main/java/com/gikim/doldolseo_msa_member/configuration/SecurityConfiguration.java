package com.gikim.doldolseo_msa_member.configuration;

import com.gikim.doldolseo_msa_member.jwt.JwtAuthenticationEntryPoint;
import com.gikim.doldolseo_msa_member.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        security.csrf().disable()
                .authorizeRequests().antMatchers("/member").permitAll()
                .and()
                .authorizeRequests().antMatchers("/member/images/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/member/check").permitAll()
                .and()
                .authorizeRequests().antMatchers("/member/login").permitAll()
                .and()
                .authorizeRequests().antMatchers("/member/refresh").permitAll()
                .and()
                .authorizeRequests().antMatchers("/member/role").permitAll()
                .and()
                .authorizeRequests().antMatchers("/member/**").hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .headers().frameOptions().disable();

        //        security.cors().configurationSource(corsConfigurationSource());
    }

    /*
        CORS 미사용 : API게이트웨이 에서 처리
    */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*");
        configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Content-type"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
