package hr.hackathon.culture_event.feature.event_recurrence;

import hr.hackathon.culture_event.feature.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "event_recurrences")
public class EventRecurrence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventRecurrence")
  private List<Event> events;

  public void addChildToList(Event event) {
    if (this.getEvents() == null) {
      this.setEvents(new ArrayList<>());
    }
    this.getEvents().add(event);
  }
}
