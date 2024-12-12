package hr.hackathon.culture_event.feature.event;

import hr.hackathon.culture_event.feature.user.User;
import hr.hackathon.culture_event.feature.user.UserRepository;
import hr.hackathon.culture_event.feature.user.UserResourceService;
import hr.hackathon.culture_event.jwt_token.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventResourceService {

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;
  private final UserResourceService userResourceService;
  private final UserRepository userRepository;
  private final JwtTokenService jwtTokenService;

  public List<EventResponse> findAll() {
    List<Event> events = eventRepository.findAllLimit50();
    return events.stream().map(eventMapper::toResponse).toList();
  }

  public EventResponse findById(Long id) {
    return eventMapper.toResponse(
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")));
  }

  public List<EventResponse> searchEventsWithFilters(
      String searchTerm,
      LocalDateTime fromDate,
      LocalDateTime toDate,
      Double maxPrice,
      String category) {

    List<Event> searchedEvents;
    List<Long> categoryIds;
    if (category != null) {
      String[] categoryNames = category.split(",");
      categoryIds = Arrays.stream(categoryNames).map(Long::parseLong).collect(Collectors.toList());
      searchedEvents = eventRepository.searchEventsWithFilters(searchTerm, maxPrice, categoryIds);
    } else {
      searchedEvents = eventRepository.searchEventsWithFilters(searchTerm, maxPrice, List.of());
    }

    return searchedEvents.stream()
        .filter(
            event -> {
              if (fromDate != null && event.getStartDate().isBefore(fromDate)) {
                return false;
              }
              if (toDate != null && event.getStartDate().isAfter(toDate)) {
                return false;
              }
              return true;
            })
        .map(eventMapper::toResponse)
        .toList();
  }

  public void favoriteEvent(Long id, String token) {
    Event event =
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

    if(token == null){
      return;
    }
    User user = jwtTokenService.getUserFromJwt(token.substring(7));
    if (user.getFavoriteEvents() == null) {
      user.setFavoriteEvents(new ArrayList<>());
    }
    if (user.getFavoriteEvents().contains(event)) {
      return;
    }
    user.getFavoriteEvents().add(event);
    userRepository.save(user);
  }

  public List<EventResponse> getFavoriteEvents(String token) {
    if(token == null){
      return new ArrayList<>();
    }
    User user = jwtTokenService.getUserFromJwt(token.substring(7));
    return user.getFavoriteEvents().stream().map(eventMapper::toResponse).toList();
  }
}
