package hr.hackathon.culture_event.feature.user.request;

import lombok.Data;

@Data
public class UserLoginRequest {

  private String username;

  private String password;
}
