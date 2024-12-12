package hr.hackathon.culture_event.feature.event;

import hr.hackathon.culture_event.feature.age_group.AgeGroup;
import hr.hackathon.culture_event.feature.event_category.EventCategory;
import hr.hackathon.culture_event.feature.event_recurrence.EventRecurrence;
import hr.hackathon.culture_event.feature.event_type.EventType;
import hr.hackathon.culture_event.feature.neighbourhood.Neighbourhood;
import hr.hackathon.culture_event.feature.organizer.Organizer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String shortName;

  private String longName;

  private String englishName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organizer_id")
  private Organizer organizer;

  private String location;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "neighbourhood_id")
  private Neighbourhood neighbourhood;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_recurrence_id")
  private EventRecurrence eventRecurrence;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime startDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime endDate;

  private Boolean childFriendlyFlag;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "events_age_groups",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "age_group_id"))
  private List<AgeGroup> ageGroups;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "events_categories",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private List<EventCategory> eventCategories;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "events_types",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "type_id"))
  private List<EventType> eventTypes;

  private String description;

  private String descriptionEnglish;

  private String eventImageUrl;

  private Double price;

  private Integer ticketAmount;

  private Boolean numberedTicketsFlag;

  private Boolean signUpRequiredFlag;

  private Boolean availableInCroatianFlag;

  private Boolean availableInEnglishFlag;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime signUpEndDate;

  private String eventUrl;
}
