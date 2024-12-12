package hr.hackathon.culture_event.feature.neighbourhood;

import hr.hackathon.culture_event.feature.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "neighbourhoods")
public class Neighbourhood {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "neighbourhood")
  private List<Event> events;

  public void addChildToList(Event event) {
    if (this.getEvents() == null) {
      this.setEvents(new ArrayList<>());
    }
    this.getEvents().add(event);
  }
}
