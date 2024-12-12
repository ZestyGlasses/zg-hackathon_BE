package hr.hackathon.culture_event.feature.event;

import hr.hackathon.culture_event.feature.event_recurrence.EventRecurrenceMapper;
import hr.hackathon.culture_event.feature.neighbourhood.NeighbourhoodMapper;
import hr.hackathon.culture_event.feature.organizer.OrganizerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {OrganizerMapper.class, EventRecurrenceMapper.class, NeighbourhoodMapper.class})
public interface EventMapper {

  @Mapping(
      target = "ageGroups",
      expression =
          "java(event.getAgeGroups().stream().map(ageGroup -> ageGroup.getName()).toList())")
  @Mapping(
      target = "eventTypes",
      expression =
          "java(event.getEventTypes().stream().map(eventType -> eventType.getName()).toList())")
  @Mapping(
      target = "eventCategories",
      expression =
          "java(event.getEventCategories().stream().map(eventCategory -> eventCategory.getName()).toList())")
  EventResponse toResponse(Event event);
}
