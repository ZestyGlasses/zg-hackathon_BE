package hr.hackathon.culture_event.feature.user.response;

import lombok.Data;

@Data
public class UserLoginResponse {

  private String jwtToken;

  private UserResponse user;
}
