package hr.hackathon.culture_event.feature.event_type;

import hr.hackathon.culture_event.feature.event_category.EventCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

  @Query("SELECT et FROM EventType et")
  @EntityGraph(attributePaths = {"events"})
  List<EventType> findAllWithChildObjects();
}
