package FitMate.FitMateBackend.cjjsWorking.config;

import FitMate.FitMateBackend.cjjsWorking.config.securityFilter.JwtExceptionFilter;
import FitMate.FitMateBackend.cjjsWorking.config.securityFilter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(Customizer.withDefaults())// CORS 활성화
            .csrf(AbstractHttpConfigurer::disable);

        httpSecurity.sessionManagement(sess ->
            sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.authenticationProvider(authenticationProvider);


        httpSecurity.authorizeHttpRequests(auth -> auth
            .requestMatchers(publicEndpoints()).permitAll()
            .requestMatchers(adminEndpoints()).hasAuthority("Admin")
            .requestMatchers(customerEndpoints()).hasAuthority("Customer")
            .anyRequest().authenticated()
        );

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(jwtExceptionFilter, JwtFilter.class);
        return httpSecurity.build();
    }

    private RequestMatcher publicEndpoints() {
        return new OrRequestMatcher(
            new AntPathRequestMatcher("/auth/login"), //admin, user login
            new AntPathRequestMatcher("/auth/refresh"), //refresh token 발급
            new AntPathRequestMatcher("/register/verify/**"), //mail server 요청 관련 기능 (ChanHa added)
            new AntPathRequestMatcher("/user/auth/withuuid"), //mail 인증이 적용된 가입기능 테스트용(나중에 막기) (ChanHa added)
            new AntPathRequestMatcher("/password/verify/mail"), //mail 인증이 적용된 가입기능 테스트용(나중에 막기) (ChanHa added)
            new AntPathRequestMatcher("/password/verify/code"), //mail 인증이 적용된 가입기능 테스트용(나중에 막기) (ChanHa added)
            new AntPathRequestMatcher("/user/auth/**"), //admin, user register
            new AntPathRequestMatcher("/workouts/**"), //workout 검색 조회 - 비회원 접근 가능 기능
            new AntPathRequestMatcher("/supplements/**"), //supplement 검색 조회 - 비회원 접근 가능 기능
            new AntPathRequestMatcher("/bodyParts/**"), //bodyPart 전체 조회 요청 - 비회원 접근 가능 기능
            new AntPathRequestMatcher("/test/**"), //테스트를 위한 api 경로 - radis 통신이나 여타의 것들을 우회하기 위한 것.


            // for develop
            new AntPathRequestMatcher("/api/admin/**")
        );
    }

    private RequestMatcher adminEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/admin/**")
        );
    }

    private RequestMatcher customerEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/user/private/**"),
                new AntPathRequestMatcher("/bodyData/**"),
                new AntPathRequestMatcher("/machines/**"),
                new AntPathRequestMatcher("/supplements/**"),
                new AntPathRequestMatcher("/recommendation/**"),
                new AntPathRequestMatcher("/myfit/**")
        );
    }
}