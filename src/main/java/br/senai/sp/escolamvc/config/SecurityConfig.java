package br.senai.sp.escolamvc.config;

import br.senai.sp.escolamvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                //.requestMatchers(new AntPathRequestMatcher("/aluno/**"))
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/bootstrap5-3-2/**"))
                .requestMatchers(new AntPathRequestMatcher("/css/**"))
                .requestMatchers(new AntPathRequestMatcher("/fontawesome-free-6.4.2-web/**"))
                .requestMatchers(new AntPathRequestMatcher("/img/**"))
                .requestMatchers(new AntPathRequestMatcher("/jquery/**"))
                .requestMatchers(new AntPathRequestMatcher("/jquery-mask/**"))
                .requestMatchers(new AntPathRequestMatcher("/js/**"));

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
        //.formLogin(Customizer.withDefaults())
        .authorizeHttpRequests((requests) -> requests
                .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                //.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                //.requestMatchers(new AntPathRequestMatcher("/aluno/**")).hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
        )
        .userDetailsService(userService)
        .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/aluno")
                .permitAll()
        )
        .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                //.invalidateHttpSession(true)
        );

        return httpSecurity.build();
    }

}
