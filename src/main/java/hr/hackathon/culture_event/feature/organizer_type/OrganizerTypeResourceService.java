package hr.hackathon.culture_event.feature.organizer_type;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizerTypeResourceService {

  private final OrganizerTypeRepository organizerTypeRepository;

  public List<OrganizerType> findAll() {
    return organizerTypeRepository.findAll();
  }

  public OrganizerType findById(Long id) {
    return organizerTypeRepository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizer type not found"));
  }
}
