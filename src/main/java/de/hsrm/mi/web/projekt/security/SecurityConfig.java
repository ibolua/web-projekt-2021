package de.hsrm.mi.web.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    FotoUserDetailsService fotoUserDetailService;

    @Bean
    PasswordEncoder passwordEncoder() { // @Bean -> Encoder woanders per @Autowired abrufbar
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authmanagerbuilder) throws Exception {
        var pwenc = passwordEncoder(); // Injection "in sich selbst" geht leider nicht

        authmanagerbuilder.inMemoryAuthentication() // "in memory" - Benutzerdatenbank anlegen
            .withUser("friedfert")
            .password(pwenc.encode("dingdong")) // Passwörter nicht im Klartext speichern
            .roles("GUCKER")
        .and()  // nächster Eintrag
            .withUser("joghurta")
            .password(pwenc.encode("geheim123"))
            .roles("PHOTOGRAPH")
        .and()
            .withUser("ibo")
            .password(pwenc.encode("ibo"))
            .roles("GUCKER", "PHOTOGRAPH")
        .and().and()
            .userDetailsService(fotoUserDetailService)
            .passwordEncoder(pwenc);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/**", "/messagebroker", "/foto/*").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/foto/*").permitAll()
            .antMatchers(HttpMethod.POST, "/foto").hasRole("PHOTOGRAPH")
            .antMatchers(HttpMethod.GET, "/foto/*/del").hasRole("PHOTOGRAPH")
            .antMatchers("/logout").permitAll()
            // .antMatchers(HttpMethod.GET, "/api/**").permitAll()
            // .antMatchers("/messagebroker").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/*").authenticated()
        .and()
            .headers().frameOptions().disable()
        .and()
            .formLogin()
            .loginPage("/login") // falls eigenes Login-Formular
            .defaultSuccessUrl("/auswahl")
            .failureUrl("/login")
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/logout")   // ist auch Default
            .logoutSuccessUrl("/")
            .permitAll()
        .and()
        .csrf().disable();


    }

}
