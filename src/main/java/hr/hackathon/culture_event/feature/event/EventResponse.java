package hr.hackathon.culture_event.feature.event;

import hr.hackathon.culture_event.feature.event_recurrence.EventRecurrenceResponse;
import hr.hackathon.culture_event.feature.neighbourhood.NeighbourhoodResponse;
import hr.hackathon.culture_event.feature.organizer.OrganizerResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventResponse {

  private Long id;

  private String shortName;

  private String longName;

  private String englishName;

  private OrganizerResponse organizer;

  private String location;

  private NeighbourhoodResponse neighbourhood;

  private EventRecurrenceResponse eventRecurrence;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private Boolean childFriendlyFlag;

  private List<String> ageGroups;

  private List<String> eventCategories;

  private List<String> eventTypes;

  private String description;

  private String descriptionEnglish;

  private String eventImageUrl;

  private Double price;

  private Integer ticketAmount;

  private Boolean numberedTicketsFlag;

  private Boolean signUpRequiredFlag;

  private Boolean availableInCroatianFlag;

  private Boolean availableInEnglishFlag;

  private LocalDateTime signUpEndDate;

  private String eventUrl;
}
