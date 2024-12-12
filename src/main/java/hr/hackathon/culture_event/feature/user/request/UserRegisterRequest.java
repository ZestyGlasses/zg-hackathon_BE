package hr.hackathon.culture_event.feature.user.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

  private String firstName;

  private String lastName;

  private String username;

  private String email;

  private String password;
}
