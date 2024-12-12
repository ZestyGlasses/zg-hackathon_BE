package hr.hackathon.culture_event.feature.event_category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event-categories")
@RequiredArgsConstructor
public class EventCategoryController {

  private final EventCategoryResourceService eventCategoryResourceService;
  private final EventCategoryMapper eventCategoryMapper;

  @GetMapping()
  public List<EventCategoryResponse> findAll() {
    return eventCategoryMapper.toResponseList(eventCategoryResourceService.findAll());
  }

  @GetMapping("/{id}")
  public EventCategoryResponse findById(@PathVariable Long id) {
    return eventCategoryMapper.toResponse(eventCategoryResourceService.findById(id));
  }
}
