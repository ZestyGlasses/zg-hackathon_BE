package hr.hackathon.culture_event.feature.event_category;

import hr.hackathon.culture_event.feature.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "event_categories")
public class EventCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "eventCategories")
  private List<Event> events;

  public void addChildToList(Event event) {
    if (this.getEvents() == null) {
      this.setEvents(new ArrayList<>());
    }
    this.getEvents().add(event);
  }
}
