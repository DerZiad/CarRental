package com.coding.app.security;

import com.coding.app.models.enums.ServerRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private static final String PRIVATE_REMEMBER_KEY = "hellofriendimsmookerzz";
    private static final int DELAY = 24 * 3600;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/*").permitAll()
                        .requestMatchers("/signup").not().authenticated()
                        .requestMatchers("/verification").authenticated()
                        .requestMatchers("/logout").authenticated()
                        .requestMatchers("/api/reserve").authenticated()
                        .requestMatchers(ServerRole.MANAGER.getPrivateSpace() + "/**").hasAnyRole(ServerRole.ADMIN.getRole(), ServerRole.MANAGER.getRole())
                        .requestMatchers(ServerRole.ADMIN.getPrivateSpace() + "/**").hasRole(ServerRole.ADMIN.getRole())
                        .requestMatchers("/").hasRole(ServerRole.CLIENT.getRole())
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login").permitAll()
                        .loginPage("/login").permitAll()
                        .successHandler(successAuthenticationHandler())
                        .failureUrl("/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logout"))
                        .logoutSuccessUrl("/")
                )
                .rememberMe(r -> r
                        .tokenValiditySeconds(DELAY)
                        .key(PRIVATE_REMEMBER_KEY)
                        .rememberMeParameter("rememberme")
                        .userDetailsService(userDetailsServiceImpl)
                )
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expose AuthenticationManager for use in services (if needed)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SuccessAuthenticationHandler successAuthenticationHandler() {
        return new SuccessAuthenticationHandler();
    }

    public static class SuccessAuthenticationHandler implements AuthenticationSuccessHandler {

        private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            final String targetUrl = determineTargetUrl(authentication);
            if (response.isCommitted()) {
                return;
            }
            redirectStrategy.sendRedirect(request, response, targetUrl);
            clearAuthenticationAttributes(request);
        }

        protected String determineTargetUrl(Authentication authentication) {

            final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (ServerRole role : ServerRole.values()) {
                if (authorities.contains(new SimpleGrantedAuthority("ROLE_" + role.getRole()))) {
                    return role.getTargetUrl();
                }
            }
            return null;
        }

        protected void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);

            if (session == null) {
                return;
            }
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
