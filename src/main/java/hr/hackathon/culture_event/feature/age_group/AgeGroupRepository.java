package hr.hackathon.culture_event.feature.age_group;

import hr.hackathon.culture_event.feature.event_type.EventType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgeGroupRepository extends JpaRepository<AgeGroup, Long> {

  @Query("SELECT ag FROM AgeGroup ag")
  @EntityGraph(attributePaths = {"events"})
  List<AgeGroup> findAllWithChildObjects();
}
