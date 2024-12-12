package hr.hackathon.culture_event.config;

import hr.hackathon.culture_event.jwt_token.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Value("${frontend.url}")
    private String frontendUrl;

  public static final List<String> UNAUTHENTICATED_ENDPOINTS =
      List.of(
          "users/generate-test-user",
          "users/register",
          "users/login",
          "organizer-types",
          "organizer-types/**",
          "event-types",
          "event-types/**",
          "event-categories",
          "event-categories/**",
          "events",
          "events/search",
          "events/favorite",
          "events/favorites",
          "swagger-ui/**",
          "v3/api-docs/**");
  public static final List<String> ADMIN_ENDPOINTS = List.of("example/test-admin");

  private final JwtTokenFilter jwtFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.cors(
            httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth -> {
              auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
              UNAUTHENTICATED_ENDPOINTS.forEach(
                  endpoint -> auth.requestMatchers(endpoint).permitAll());
              ADMIN_ENDPOINTS.forEach(endpoint -> auth.requestMatchers(endpoint).hasRole("ADMIN"));
              auth.anyRequest().authenticated();
            })
        .formLogin(
            login ->
                login
                    .defaultSuccessUrl("/web", true)
                    .failureUrl("/login.html?error=true"))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                    (request, response, authException) ->
                        response.setStatus(HttpStatus.UNAUTHORIZED.value())))
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(
        List.of("http://localhost:5173", "http://localhost:8080", frontendUrl)); // Adjust the allowed origins
    configuration.setAllowedMethods(
        Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.DELETE.name()));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
