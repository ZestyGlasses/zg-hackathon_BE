package hr.hackathon.culture_event.feature.neighbourhood;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NeighbourhoodRepository extends JpaRepository<Neighbourhood, Long> {

  @Query("SELECT n FROM Neighbourhood n")
  @EntityGraph(attributePaths = {"events"})
  List<Neighbourhood> findAllWithChildObjects();
}
