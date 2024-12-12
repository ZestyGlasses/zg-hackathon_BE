package hr.hackathon.culture_event.feature.organizer;

import lombok.Data;

import java.util.List;

@Data
public class OrganizerResponse {

  private Long id;

  private String name;

  private List<String> organizerTypes;

  private String location;

  private String contactEmail;

  private Boolean parkingAvailableFlag;

  private Boolean animalsAllowedFlag;

  private Boolean disabilityEntranceFlag;

  private Boolean disabilityToiletFlag;
}
