package hr.hackathon.culture_event.feature.organizer;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    @Query("SELECT o FROM Organizer o")
    @EntityGraph(attributePaths = {"events"})
    List<Organizer> findAllWithChildObjects();

}
