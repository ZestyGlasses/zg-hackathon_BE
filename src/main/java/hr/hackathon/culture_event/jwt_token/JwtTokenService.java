package hr.hackathon.culture_event.jwt_token;

import hr.hackathon.culture_event.feature.user.User;
import hr.hackathon.culture_event.feature.user.UserRepository;
import hr.hackathon.culture_event.feature.user.role.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
  private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);

  @Value("604800")
  private Long accessTokenValiditySeconds;

  @Value("${security.authentication.jwt.base64-secret}")
  private String secretKey;

  private final UserRepository userRepository;

  private Set<String> invalidatedTokens = new HashSet<>();

  public boolean authenticate(String token) {
    // If JWT is invalid, user can not be authenticated
    if (isJwtInvalid(token)) {
      return false;
    }
    // If JWT is valid, store authentication in Spring security context
    User applicationUser = getUserFromJwt(token);
    saveAuthentication(applicationUser);

    return true;
  }

  public String createJwt(User jwtUser) {
    String roles = jwtUser.getRoles().stream().map(Role::name).collect(Collectors.joining(","));

    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .setSubject(jwtUser.getUsername())
        .claim("id", jwtUser.getId())
        .claim("roles", roles)
        .claim("sub", jwtUser.getUsername())
        .setExpiration(
            new Date(Instant.now().plusSeconds(accessTokenValiditySeconds).toEpochMilli()))
        .setIssuedAt(new Date())
        .compact();
  }

  private boolean isJwtInvalid(String jwtToken) {
    try {
      if (invalidatedTokens.contains(jwtToken)) {
        return true;
      }
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return false;
    } catch (SignatureException e) {
      log.debug("Invalid JWT signature.");
      log.trace("Invalid JWT signature trace: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.debug("Invalid JWT token.");
      log.trace("Invalid JWT token trace: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.debug("Expired JWT token.");
      log.trace("Expired JWT token trace: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.debug("Unsupported JWT token.");
      log.trace("Unsupported JWT token trace: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.debug("JWT token compact of handler are invalid.");
      log.trace("JWT token compact of handler are invalid trace: {}", e.getMessage());
    }
    return true;
  }

  public String getCurrentUserJwtToken() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
  }

  public User getUserFromJwt(String jwtToken) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    return userRepository
        .findById(Long.valueOf(claims.get("id").toString()))
        .orElseThrow(() -> new RuntimeException("User not found from JWT"));
  }

  public List<String> getUserRolesFromJwt(String jwtToken) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    return Arrays.asList(claims.get("roles").toString().split(","));
  }

  public String getUsernameFromJwt(String jwtToken) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    return claims.getSubject();
  }

  private void saveAuthentication(User applicationUser) {
    List<SimpleGrantedAuthority> authorities =
        applicationUser.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(applicationUser, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void invalidateToken(String token) {
    invalidatedTokens.add(token);
  }
}
