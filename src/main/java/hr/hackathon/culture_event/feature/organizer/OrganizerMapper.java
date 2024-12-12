package hr.hackathon.culture_event.feature.organizer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizerMapper {

  @Mapping(
      target = "organizerTypes",
      expression =
          "java(organizer.getTypes().stream().map(organizerType -> organizerType.getName()).toList())")
  OrganizerResponse toResponse(Organizer organizer);
}
