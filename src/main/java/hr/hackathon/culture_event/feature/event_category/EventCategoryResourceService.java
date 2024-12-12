package hr.hackathon.culture_event.feature.event_category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventCategoryResourceService {

  private final EventCategoryRepository eventCategoryRepository;

  public List<EventCategory> findAll() {
    return eventCategoryRepository.findAll();
  }

  public EventCategory findById(Long id) {
    return eventCategoryRepository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event category not found"));
  }
}
