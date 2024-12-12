package hr.hackathon.culture_event.feature.event_type;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event-types")
@RequiredArgsConstructor
public class EventTypeController {

  private final EventTypeResourceService eventTypeResourceService;
  private final EventTypeMapper eventTypeMapper;

  @GetMapping()
  public List<EventTypeResponse> findAll() {
    return eventTypeMapper.toResponseList(eventTypeResourceService.findAll());
  }

  @GetMapping("/{id}")
  public EventTypeResponse findById(@PathVariable Long id) {
    return eventTypeMapper.toResponse(eventTypeResourceService.findById(id));
  }
}
