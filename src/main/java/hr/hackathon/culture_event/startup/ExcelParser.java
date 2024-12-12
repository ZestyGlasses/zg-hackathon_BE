package hr.hackathon.culture_event.startup;

import hr.hackathon.culture_event.feature.age_group.AgeGroup;
import hr.hackathon.culture_event.feature.age_group.AgeGroupRepository;
import hr.hackathon.culture_event.feature.event.Event;
import hr.hackathon.culture_event.feature.event.EventRepository;
import hr.hackathon.culture_event.feature.event_category.EventCategory;
import hr.hackathon.culture_event.feature.event_category.EventCategoryRepository;
import hr.hackathon.culture_event.feature.event_recurrence.EventRecurrence;
import hr.hackathon.culture_event.feature.event_recurrence.EventRecurrenceRepository;
import hr.hackathon.culture_event.feature.event_type.EventType;
import hr.hackathon.culture_event.feature.event_type.EventTypeRepository;
import hr.hackathon.culture_event.feature.neighbourhood.Neighbourhood;
import hr.hackathon.culture_event.feature.neighbourhood.NeighbourhoodRepository;
import hr.hackathon.culture_event.feature.organizer.Organizer;
import hr.hackathon.culture_event.feature.organizer.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ExcelParser {

  private final EventRepository eventRepository;
  private final OrganizerRepository organizerRepository;
  private final EventRecurrenceRepository eventRecurrenceRepository;
  private final NeighbourhoodRepository neighbourhoodRepository;
  private final EventCategoryRepository eventCategoryRepository;
  private final EventTypeRepository eventTypeRepository;
  private final AgeGroupRepository ageGroupRepository;

  public void parseExcel() throws IOException {

    if (!(eventRepository.count() > 0)) {
      List<Organizer> organizers = organizerRepository.findAllWithChildObjects();
      List<EventRecurrence> eventRecurrences = eventRecurrenceRepository.findAllWithChildObjects();
      List<Neighbourhood> neighbourhoods = neighbourhoodRepository.findAllWithChildObjects();
      List<EventCategory> allEventCategories = eventCategoryRepository.findAllWithChildObjects();
      List<EventType> allEventTypes = eventTypeRepository.findAllWithChildObjects();
      List<AgeGroup> allAgeGroups = ageGroupRepository.findAllWithChildObjects();

      InputStream file = getClass().getClassLoader().getResourceAsStream("excel/Events.xlsx");
      assert file != null;
      Workbook workbook = new XSSFWorkbook(file);
      Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

      Iterator<Row> rowIterator = sheet.iterator();
      rowIterator.next(); // Skip header row

      List<Event> events = new ArrayList<>();

      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        Event event = new Event();

        // Read each column and set values in the Event entity

        String shortName = getStringCellValue(row.getCell(0));

        if (shortName == null || shortName.isEmpty()) {
          continue;
        }
        event.setShortName(shortName);
        event.setLongName(getStringCellValue(row.getCell(1)));
        event.setEnglishName(getStringCellValue(row.getCell(2)));

        Organizer organizer = findByName(organizers, getStringCellValue(row.getCell(3)));
        event.setOrganizer(organizer);
        if (organizer != null) {
          organizer.addChildToList(event);
        }

        event.setLocation(getStringCellValue(row.getCell(4)));

        Neighbourhood neighbourhood =
            findByNeighbourhood(neighbourhoods, getStringCellValue(row.getCell(5)));
        event.setNeighbourhood(neighbourhood);
        if (neighbourhood != null) {
          neighbourhood.addChildToList(event);
        }

        EventRecurrence eventRecurrence =
            findByRecurrence(eventRecurrences, getStringCellValue(row.getCell(6)));
        event.setEventRecurrence(eventRecurrence);
        if (eventRecurrence != null) {
          eventRecurrence.addChildToList(event);
        }

        event.setStartDate(getLocalDateTime(row.getCell(7)));
        event.setEndDate(getLocalDateTime(row.getCell(8)));
        event.setChildFriendlyFlag(getBooleanValue(getStringCellValue(row.getCell(9))));
        event.setDescription(getStringCellValue(row.getCell(10)));
        event.setDescriptionEnglish(getStringCellValue(row.getCell(11)));
        event.setPrice(parsePrice(getStringCellValue(row.getCell(12))));
        event.setTicketAmount(getNumericCellValue(row.getCell(13)));
        event.setNumberedTicketsFlag(getBooleanValue(getStringCellValue(row.getCell(14))));
        event.setSignUpRequiredFlag(getBooleanValue(getStringCellValue(row.getCell(15))));
        event.setAvailableInCroatianFlag(getBooleanValue(getStringCellValue(row.getCell(16))));
        event.setAvailableInEnglishFlag(getBooleanValue(getStringCellValue(row.getCell(17))));
        event.setSignUpEndDate(getLocalDateTime(row.getCell(18)));
        event.setEventUrl(getStringCellValue(row.getCell(19)));

        List<EventCategory> eventCategories =
            parseCategories(allEventCategories, getStringCellValue(row.getCell(20)));
        event.setEventCategories(eventCategories);
        if (eventCategories != null) {
          eventCategories.forEach(c -> c.addChildToList(event));
        }

        List<EventType> eventTypes = parseTypes(allEventTypes, getStringCellValue(row.getCell(21)));
        event.setEventTypes(eventTypes);
        if (eventTypes != null) {
          eventTypes.forEach(t -> t.addChildToList(event));
        }

        List<AgeGroup> ageGroups =
            parseAgeGroups(allAgeGroups, getStringCellValue(row.getCell(22)));
        event.setAgeGroups(ageGroups);
        if (ageGroups != null) {
          ageGroups.forEach(a -> a.addChildToList(event));
        }
        event.setEventImageUrl(getStringCellValue(row.getCell(23)));
        events.add(event);
      }
      eventRepository.saveAll(events);
    }
  }

  private String getStringCellValue(Cell cell) {
    return (cell != null && cell.getCellType() == CellType.STRING)
        ? cell.getStringCellValue()
        : null;
  }

  private LocalDateTime getLocalDateTime(Cell cell) {
    if (cell != null
        && cell.getCellType() == CellType.STRING
        && !cell.getStringCellValue().isEmpty()) {
      String cellValue = cell.getStringCellValue();

      // Try parsing the first format: "MM/dd/yyyy HH:mm"
      DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
      try {
        return LocalDateTime.parse(cellValue, formatter1);
      } catch (DateTimeParseException e) {
        // If the first format fails, try the second format: "dd.MM.yyyy HH:mm:ss"
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm:ss");
        try {
          return LocalDateTime.parse(cellValue, formatter2);
        } catch (DateTimeParseException ex) {
          // Handle case where both formats fail
          return null;
        }
      }
    }
    return null; // Return null if parsing fails or cell is empty
  }

  private Integer getNumericCellValue(Cell cell) {
    return (cell != null && cell.getCellType() == CellType.NUMERIC)
        ? (int) cell.getNumericCellValue()
        : null;
  }

  private Organizer findByName(List<Organizer> organizers, String name) {
    return organizers.stream().filter(o -> o.getName().equals(name)).findFirst().orElse(null);
  }

  private EventRecurrence findByRecurrence(List<EventRecurrence> eventRecurrences, String name) {
    return eventRecurrences.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
  }

  private Neighbourhood findByNeighbourhood(List<Neighbourhood> neighbourhoods, String name) {
    return neighbourhoods.stream().filter(n -> n.getName().equals(name)).findFirst().orElse(null);
  }

  private Boolean getBooleanValue(String value) {
    if (value != null && value.equals("checked")) return true;
    else return false;
  }

  private List<EventCategory> parseCategories(
      List<EventCategory> allEventCategories, String stringCategories) {
    if (stringCategories != null && !stringCategories.isEmpty()) {
      String[] categoryNames = stringCategories.split(",");
      return allEventCategories.stream()
          .filter(c -> Arrays.asList(categoryNames).contains(c.getName()))
          .toList();
    }
    return null;
  }

  private List<EventType> parseTypes(List<EventType> allEventTypes, String stringTypes) {
    if (stringTypes != null && !stringTypes.isEmpty()) {
      String[] typeNames = stringTypes.split(",");
      return allEventTypes.stream()
          .filter(t -> Arrays.asList(typeNames).contains(t.getName()))
          .toList();
    }
    return null;
  }

  private List<AgeGroup> parseAgeGroups(List<AgeGroup> allAgeGroups, String stringAgeGroups) {
    if (stringAgeGroups != null && !stringAgeGroups.isEmpty()) {
      String[] ageGroupNames = stringAgeGroups.split(",");
      return allAgeGroups.stream()
          .filter(a -> Arrays.asList(ageGroupNames).contains(a.getName()))
          .toList();
    }
    return null;
  }

  private Double parsePrice(String price) {
    if (price != null && !price.isEmpty()) {
      price = price.substring(1).replace(",", "");
      return Double.parseDouble(price);
    }
    return null;
  }
}
