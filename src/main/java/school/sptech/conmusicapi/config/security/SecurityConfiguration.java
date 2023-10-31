package school.sptech.conmusicapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import school.sptech.conmusicapi.config.security.jwt.JwtTokenManager;
import school.sptech.conmusicapi.modules.user.services.AuthenticationService;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String ALLOWED_ORIGINS = "*";

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;

    private static final AntPathRequestMatcher[] ALLOWED_URLS = {
            new AntPathRequestMatcher("/configuration/ui"),
            new AntPathRequestMatcher("/configuration/security"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/api/public/**"),
            new AntPathRequestMatcher("/api/public/authenticate"),
            new AntPathRequestMatcher("/actuator/*"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/users/authentication"),
            new AntPathRequestMatcher("/artists/**"),
            new AntPathRequestMatcher("/managers", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/error/**"),
            new AntPathRequestMatcher("/genre/**")
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions().disable()
                .and().cors().configurationSource(request -> buildCorsConfiguration())
                .and().csrf().disable()
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(ALLOWED_URLS)
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                    .exceptionHandling()
                    .authenticationEntryPoint(apiAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new ApiAuthenticationProvider(authenticationService, passwordEncoder()));
        return authenticationManagerBuilder.build();
    }

    @Bean
    public ApiAuthenticationEntryPoint jwtAuthenticationEntryPointBean() {
        return new ApiAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationFilter jwtAuthenticationFilterBean() {
        return new AuthenticationFilter(authenticationService, jwtAuthenticationUtilBean());
    }

    @Bean
    public JwtTokenManager jwtAuthenticationUtilBean() {
        return new JwtTokenManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CorsConfiguration buildCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList(ALLOWED_ORIGINS));
        configuration.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name()
                )
        );

        configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION));
        return configuration;
    }
}
