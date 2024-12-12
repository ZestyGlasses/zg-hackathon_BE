package hr.hackathon.culture_event.feature.event_category;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {

  @Query("SELECT ec FROM EventCategory ec")
  @EntityGraph(attributePaths = {"events"})
  List<EventCategory> findAllWithChildObjects();
}
