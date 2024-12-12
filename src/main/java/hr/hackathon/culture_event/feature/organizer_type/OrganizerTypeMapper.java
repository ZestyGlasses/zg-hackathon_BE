package hr.hackathon.culture_event.feature.organizer_type;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizerTypeMapper {

  OrganizerTypeResponse toResponse(OrganizerType organizerType);

  List<OrganizerTypeResponse> toResponse(List<OrganizerType> organizerTypes);
}
