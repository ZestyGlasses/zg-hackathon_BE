package hr.hackathon.culture_event.feature.organizer_type;

import hr.hackathon.culture_event.feature.organizer.Organizer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "organizer_types")
public class OrganizerType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Organizer> organizer;
}
