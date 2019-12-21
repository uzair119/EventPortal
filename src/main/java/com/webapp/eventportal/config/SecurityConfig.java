package com.webapp.eventportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Qualifier("userService")
    @Autowired
    UserDetailsService userDetailsService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .ignoringAntMatchers("/**").and()
                //.csrf().disable()

                .authorizeRequests()
                .antMatchers("/auth/admin/**").hasAuthority("ADMIN")
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                //.loginPage("/")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .logoutUrl("/logout").deleteCookies().clearAuthentication(true).permitAll()
                .and()
                .cors().configurationSource(configurationSource());
    }
    private CorsConfigurationSource configurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        //config.addAllowedHeader("Access-Control-Allow-Headers");
        config.addAllowedHeader("Date");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Accept");;
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("From");
        config.addAllowedHeader("X-Auth-Token");
        config.addAllowedHeader("Request-Id");
        //config.addAllowedHeader("Access-Control-Expose-Headers");
        //config.addAllowedHeader("Set-Cookie");
        config.addExposedHeader("Set-Cookie");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    private AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
                handle(httpServletRequest, httpServletResponse, authentication);
            }
        };
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = "/auth/user/me";
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private AuthenticationFailureHandler failureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().append("{\"msg\":\"Authentication failure\"}");
                httpServletResponse.setStatus(401);
            }
        };
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().append("{\"msg\":\"Access Denied.\"}");
                httpServletResponse.setStatus(403);
            }
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().append("{\"msg\":\"Not authenticated.\"}");
                httpServletResponse.setStatus(401);
            }
        };
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().append("{\"msg\":\"Logged out.\"}");
                httpServletResponse.setStatus(200);
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}