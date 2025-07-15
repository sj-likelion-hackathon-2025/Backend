package org.kwakmunsu.flowmate.global.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.global.jwt.filter.JwtFilter;
import org.kwakmunsu.flowmate.global.jwt.handler.JwtAccessDeniedHandler;
import org.kwakmunsu.flowmate.global.jwt.handler.JwtAuthenticationEntryPoint;
import org.kwakmunsu.flowmate.global.jwt.provider.JwtProvider;
import org.kwakmunsu.flowmate.global.oauth2.handler.CustomOAuth2FailureHandler;
import org.kwakmunsu.flowmate.global.oauth2.handler.CustomOAuth2SuccessHandler;
import org.kwakmunsu.flowmate.global.oauth2.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customSuccessHandler;
    private final CustomOAuth2FailureHandler customOauth2FailureHandler;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**")
                        .permitAll()
                );

        http
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint( //  **사용자 정보(UserInfo)**를 가져오는 엔드포인트를 설정하는 부분
                                userInfoEndpointConfig -> userInfoEndpointConfig
                                        .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                        .failureHandler(customOauth2FailureHandler)
                );
        http
                .addFilterBefore(new JwtFilter(jwtProvider, objectMapper), UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling(handle -> handle
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler));

        http
                .cors(
                        corsCustomizer -> corsCustomizer.configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("http://localhost:5173"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            config.setAllowedHeaders(List.of("*"));
                            config.setAllowCredentials(true);
                            config.setMaxAge(3600L);
                            config.setExposedHeaders(List.of("Authorization"));

                            return config;
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}