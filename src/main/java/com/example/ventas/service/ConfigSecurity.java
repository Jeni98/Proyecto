package com.example.ventas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailService;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth.userDetailsService(userDetailService).passwordEncoder(getEnecode());

        //auth.inMemoryAuthentication()
                // cifrado
          //      .passwordEncoder(new BCryptPasswordEncoder())
        //    .withUser("admin")
          //      .password(new BCryptPasswordEncoder().encode("123456"))
           //     .roles();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
       //csrf-> no deja que le entre codigo malicioso
        http.csrf().disable().authorizeRequests()
                .antMatchers("/administrador/**").hasRole("ADMIN")
                .antMatchers("/productos/**").hasRole("ADMIN")
                .and().formLogin().loginPage("/usuario/login")
               .permitAll().defaultSuccessUrl("/usuario/acceder");
    }

    @Bean
    public static BCryptPasswordEncoder getEnecode(){
        return new BCryptPasswordEncoder();
    }
}
