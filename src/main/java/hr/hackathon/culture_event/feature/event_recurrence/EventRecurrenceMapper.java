package hr.hackathon.culture_event.feature.event_recurrence;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventRecurrenceMapper {

  EventRecurrenceResponse toResponse(EventRecurrence eventRecurrence);
}
