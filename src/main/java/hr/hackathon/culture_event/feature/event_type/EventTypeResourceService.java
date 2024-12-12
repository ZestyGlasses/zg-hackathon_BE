package hr.hackathon.culture_event.feature.event_type;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventTypeResourceService {

  private final EventTypeRepository eventTypeRepository;

  public List<EventType> findAll() {
    return eventTypeRepository.findAll();
  }

  public EventType findById(Long id) {
    return eventTypeRepository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event type not found"));
  }
}
