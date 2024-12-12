package hr.hackathon.culture_event.feature.user;

import hr.hackathon.culture_event.config.AuditorConfig;
import hr.hackathon.culture_event.feature.user.request.UserLoginRequest;
import hr.hackathon.culture_event.feature.user.request.UserRegisterRequest;
import hr.hackathon.culture_event.feature.user.request.UserUpdateRequest;
import hr.hackathon.culture_event.feature.user.response.UserLoginResponse;
import hr.hackathon.culture_event.feature.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserResourceService userResourceService;
  private final UserMapper userMapper;
  private final AuditorConfig auditorConfig;

  @GetMapping("/generate-test-user")
  public UserLoginResponse testUser() {
    return userResourceService.generateTestUser();
  }

  @PostMapping("/login")
  public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest) {
    return userResourceService.login(userLoginRequest);
  }

  @PostMapping("/register")
  public UserLoginResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
    return userResourceService.register(userRegisterRequest);
  }

  @PostMapping("/logout")
  public void logout() {
    userResourceService.logout();
  }

  @GetMapping("/user-info")
  public UserResponse getUserInfo() {
    return userMapper.toResponse(
        userResourceService.getUserInfo(auditorConfig.getCurrentAuditor().get()));
  }

  @PutMapping()
  public UserResponse updateUser(@RequestBody UserUpdateRequest request) {
    return userMapper.toResponse(
        userResourceService.update(auditorConfig.getCurrentAuditor().get(), request));
  }

  @DeleteMapping()
  public void deleteUser() {
    userResourceService.delete(auditorConfig.getCurrentAuditor().get());
  }
}
