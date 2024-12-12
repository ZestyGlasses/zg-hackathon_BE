package hr.hackathon.culture_event.feature.user.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

  private Long id;

  private String firstName;

  private String lastName;

  private String username;

  private String email;

  private List<String> roles;
}
