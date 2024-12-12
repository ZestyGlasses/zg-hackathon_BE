package hr.hackathon.culture_event.feature.user;

import hr.hackathon.culture_event.feature.user.request.UserRegisterRequest;
import hr.hackathon.culture_event.feature.user.request.UserUpdateRequest;
import hr.hackathon.culture_event.feature.user.response.UserLoginResponse;
import hr.hackathon.culture_event.feature.user.response.UserResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "password", ignore = true)
  User toEntity(UserRegisterRequest userRegisterRequest);

  UserResponse toResponse(User user);

  @Mapping(target = "jwtToken", ignore = true)
  UserLoginResponse toLoginResponse(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @InheritConfiguration(name = "toEntity")
  User update(UserUpdateRequest request, @MappingTarget User target);
}
