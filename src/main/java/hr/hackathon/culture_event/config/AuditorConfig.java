package hr.hackathon.culture_event.config;

import hr.hackathon.culture_event.feature.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AuditorConfig implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      return Optional.empty();
    }

    if(auth.getPrincipal() instanceof String) {
      return Optional.of((String) auth.getPrincipal());
    }
    User user = (User) auth.getPrincipal();
    return Optional.of(user.getUsername());
  }
}
