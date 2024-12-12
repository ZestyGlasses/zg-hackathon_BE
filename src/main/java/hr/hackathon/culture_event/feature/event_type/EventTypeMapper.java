package hr.hackathon.culture_event.feature.event_type;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {

  EventTypeResponse toResponse(EventType eventType);

  List<EventTypeResponse> toResponseList(List<EventType> eventTypes);
}
