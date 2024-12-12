package hr.hackathon.culture_event.feature.user;

import hr.hackathon.culture_event.feature.user.request.UserLoginRequest;
import hr.hackathon.culture_event.feature.user.request.UserRegisterRequest;
import hr.hackathon.culture_event.feature.user.request.UserUpdateRequest;
import hr.hackathon.culture_event.feature.user.response.UserLoginResponse;
import hr.hackathon.culture_event.feature.user.role.Role;
import hr.hackathon.culture_event.jwt_token.JwtTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Transactional
@RequiredArgsConstructor
public class UserResourceService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtTokenService jwtTokenService;

  public User findById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "User not found"));
  }

  public User findByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "User not found"));
  }

  public UserLoginResponse generateTestUser() {
    User user = new User();
    user.setFirstName("Test");
    user.setLastName("User");
    user.setUsername("user" + System.currentTimeMillis());
    user.setEmail("testemail" + System.currentTimeMillis() + "@test.com");
    user.setPassword(bCryptPasswordEncoder.encode("password"));
    user.setRoles(Set.of(Role.ROLE_USER));
    return createLoginResponse(userRepository.save(user));
  }

  public UserLoginResponse login(UserLoginRequest loginRequest) {
    User user = findByUsername(loginRequest.getUsername());
    if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(BAD_REQUEST, "Invalid password.");
    }
    return createLoginResponse(user);
  }

  public UserLoginResponse register(UserRegisterRequest userRegisterRequest) {
    User newUser = userMapper.toEntity(userRegisterRequest);
    newUser.setPassword(new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword()));
    newUser.setRoles(Set.of(Role.ROLE_USER));
    userRepository.save(newUser);
    return createLoginResponse(newUser);
  }

  public UserLoginResponse registerAdmin(UserRegisterRequest userRegisterRequest) {
    User newUser = userMapper.toEntity(userRegisterRequest);
    newUser.setPassword(new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword()));
    newUser.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));
    userRepository.save(newUser);
    return createLoginResponse(newUser);
  }

  private UserLoginResponse createLoginResponse(User user) {
    UserLoginResponse userLoginResponse = userMapper.toLoginResponse(user);
    userLoginResponse.setJwtToken(jwtTokenService.createJwt(user));
    return userLoginResponse;
  }

  public void logout() {
    String jwtToken = jwtTokenService.getCurrentUserJwtToken();
    jwtTokenService.invalidateToken(jwtToken);
  }

  public User getUserInfo(String username) {
    return findByUsername(username);
  }

  public User update(String username, UserUpdateRequest request) {
    User user = findByUsername(username);
    return userRepository.save(userMapper.update(request, user));
  }

  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }
}
