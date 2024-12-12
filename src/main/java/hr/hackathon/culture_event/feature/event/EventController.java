package hr.hackathon.culture_event.feature.event;

import hr.hackathon.culture_event.config.AuditorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

  private final EventResourceService eventResourceService;
  private final AuditorConfig auditorConfig;

  @GetMapping()
  public List<EventResponse> findAll() {
    return eventResourceService.findAll();
  }

  @GetMapping("/{id}")
  public EventResponse findById(@PathVariable Long id) {
    return eventResourceService.findById(id);
  }

  @GetMapping("/search")
  public List<EventResponse> searchEvents(
      @RequestParam(value = "q", required = false) String query,
      @RequestParam(value = "fromDate", required = false) Long fromDate,
      @RequestParam(value = "toDate", required = false) Long toDate,
      @RequestParam(value = "price", required = false) String price,
      @RequestParam(value = "category", required = false) String category) {
    LocalDateTime fromDateEpoch =
        fromDate != null
            ? LocalDateTime.ofInstant(Instant.ofEpochMilli(fromDate), ZoneId.systemDefault())
            : null;
    LocalDateTime toDateEpoch =
        toDate != null
            ? LocalDateTime.ofInstant(Instant.ofEpochMilli(toDate), ZoneId.systemDefault())
            : null;
    Double maxPrice = null;

    // Handle price filter (e.g., "under-25")
    if (price != null && price.startsWith("under-")) {
      try {
        maxPrice = Double.parseDouble(price.substring(6));
      } catch (NumberFormatException ignored) {
      }
    }
    return eventResourceService.searchEventsWithFilters(
        query, fromDateEpoch, toDateEpoch, maxPrice, category);
  }

  @PostMapping("/favorite")
  public void favoriteEvent(@RequestBody FavoriteEventRequest request, @RequestHeader("Authorization") String token) {
    eventResourceService.favoriteEvent(request.getId(), token);
  }

  @GetMapping("/favorites")
  public List<EventResponse> getFavoriteEvents(@RequestHeader("Authorization") String token) {
    return eventResourceService.getFavoriteEvents(token);
  }
}
