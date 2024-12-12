package hr.hackathon.culture_event.feature.age_group;

import hr.hackathon.culture_event.feature.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "age_groups")
public class AgeGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ageGroups")
  private List<Event> events;

  public void addChildToList(Event event) {
    if (this.getEvents() == null) {
      this.setEvents(new java.util.ArrayList<>());
    }
    this.getEvents().add(event);
  }
}
