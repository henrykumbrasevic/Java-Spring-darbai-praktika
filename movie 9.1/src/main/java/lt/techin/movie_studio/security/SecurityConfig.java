package lt.techin.movie_studio.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


  @Value("${jwt.public.key}")
  private RSAPublicKey publicKey;

  @Value("${jwt.private.key}")
  private RSAPrivateKey privateKey;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/movies").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/movies/{id}").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/actors").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/actors/{id}").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.POST, "/api/movies").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/users").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/movies/{id}").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/movie/{id}").hasAuthority("SCOPE_ROLE_ADMIN")
                    .anyRequest().authenticated())

            .csrf(c -> c.disable())
            .httpBasic(Customizer.withDefaults())
            .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling((exceptions) -> exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

    return new NimbusJwtEncoder(jwks);
  }
}
