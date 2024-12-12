package hr.hackathon.culture_event.feature.event_recurrence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRecurrenceRepository extends JpaRepository<EventRecurrence, Long> {

  @Query("SELECT n FROM EventRecurrence n")
  @EntityGraph(attributePaths = {"events"})
  List<EventRecurrence> findAllWithChildObjects();
}
