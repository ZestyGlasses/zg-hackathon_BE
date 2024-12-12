package hr.hackathon.culture_event.feature.event_category;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventCategoryMapper {

  EventCategoryResponse toResponse(EventCategory eventCategory);

  List<EventCategoryResponse> toResponseList(List<EventCategory> eventCategories);
}
