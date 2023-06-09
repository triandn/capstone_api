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

    private final String OWNER = RoleEnum.OWNER.toString();


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
                    .antMatchers("/ws/**").permitAll()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/profile/**").hasAnyAuthority(USER,ADMIN,OWNER)
                    .antMatchers("/guest/**").hasAnyAuthority(USER,ADMIN,OWNER)
                    .antMatchers("/payment/**").hasAnyAuthority(USER,ADMIN,OWNER)
                    .antMatchers("/tour/create/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/tour/{categoryName}").permitAll()
                    .antMatchers("/tour/tour-detail/{tourId}").permitAll()
                    .antMatchers("/tour/tour-delete/{id}").hasAnyAuthority(USER,ADMIN,OWNER)
                    .antMatchers("/tour/tour-update/{id}").hasAnyAuthority(USER,ADMIN,OWNER)
                    .antMatchers("/tour/tour-owner/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/tour/tour-update-time/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/categories/").permitAll()
                    .antMatchers("/categories/{id}").permitAll()
                    .antMatchers("/categories/create/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/categories/delete/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/categories/update/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/day-booking/create/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/day-booking/delete/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/day-booking/update-day-time/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/day-booking/all/").permitAll()
                    .antMatchers("/day-booking/detail/{id}").permitAll()
                    .antMatchers("/day-booking/day-time/{tour_id}/{start_time}/{end_time}").permitAll()
                    .antMatchers("/day-booking/day-paging/all/{tour_id}/{start_time}/{end_time}").permitAll()
                    .antMatchers("/day-booking/day/all/{tour_id}").permitAll()
                    .antMatchers("/image/create/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/image/image-delete/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/image/image-update/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/image/all/").permitAll()
                    .antMatchers("/review/").permitAll()
                    .antMatchers("/review/review-tour/{tour_id}").permitAll()
                    .antMatchers("/review/create/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/review/delete/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/review/update/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/time-book/all/").permitAll()
                    .antMatchers("/time-book/detail/{id}").permitAll()
                    .antMatchers("/time-book/list-time/{day_id}").permitAll()
                    .antMatchers("/time-book/create/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/time-book/create-list/").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/time-book/delete/{id}").hasAnyAuthority(USER,OWNER)
                    .antMatchers("/time-book/update/{id}").hasAnyAuthority(USER,OWNER)
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
//        configuration.setAllowCredentials(true);
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
