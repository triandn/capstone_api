package com.example.capstone_be.security;


import com.example.capstone_be.util.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfiguration {

    private  final String ADMIN = RoleEnum.ADMIN.toString();

    private final String USER = RoleEnum.USER.toString();


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebSecurityConfiguration() {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        System.out.println("Role 2:" + USER);
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/refreshtoken").permitAll()
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/tour/create/").hasAuthority(USER)
                    .antMatchers("/tour/{categoryName}").permitAll()
                    .antMatchers("/tour/tour-detail/{tourId}").permitAll()
                    .antMatchers("/tour/tour-delete/{id}").hasAnyAuthority(USER,ADMIN)
                    .antMatchers("/tour/tour-update/{id}").hasAnyAuthority(USER,ADMIN)
                    .antMatchers("/categories").permitAll()
                    .antMatchers("/categories/{id}").permitAll()
                    .antMatchers("/categories/create/").hasAuthority(USER)
                    .antMatchers("/categories/delete/{id}").hasAuthority(USER)
                    .antMatchers("/categories/update/{id}").hasAuthority(USER)
                    .antMatchers("/day-booking/create/").hasAuthority(USER)
                    .antMatchers("/day-booking//delete/{id}").hasAuthority(USER)
                    .antMatchers("/day-booking/update/{id}").hasAuthority(USER)
                    .antMatchers("/day-booking/all/").permitAll()
                    .antMatchers("/day-booking/detail/{id}").permitAll()
                    .antMatchers("/image/create/").hasAuthority(USER)
                    .antMatchers("/image/image-delete/{id}").hasAuthority(USER)
                    .antMatchers("/image/image-update/{id}").hasAuthority(USER)
                    .antMatchers("/image/all/").permitAll()
                    .antMatchers("/review/").permitAll()
                    .antMatchers("/review/review-tour/{tour_id}").permitAll()
                    .antMatchers("/review/create/").hasAuthority(USER)
                    .antMatchers("/review/delete/{id}").hasAuthority(USER)
                    .antMatchers("/review/update/{id}").hasAuthority(USER)
                    .antMatchers("/time-book/all/").permitAll()
                    .antMatchers("/time-book/detail/{id}").permitAll()
                    .antMatchers("/time-book/create/").hasAuthority(USER)
                    .antMatchers("/time-book/create-list/").hasAuthority(USER)
                    .antMatchers("/time-book/delete/{id}").hasAuthority(USER)
                    .antMatchers("/time-book/update/{id}").hasAuthority(USER)
                    .antMatchers(HttpHeaders.ALLOW).permitAll()
                    .anyRequest().authenticated().and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(List.of("x-auth-token"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
