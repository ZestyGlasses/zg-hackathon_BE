package hr.hackathon.culture_event.feature.organizer_type;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/organizer-types")
@RequiredArgsConstructor
public class OrganizerTypeController {

  private final OrganizerTypeResourceService organizerTypeResourceService;
  private final OrganizerTypeMapper organizerTypeMapper;

  @GetMapping()
  public List<OrganizerTypeResponse> findAll() {
    return organizerTypeMapper.toResponse(organizerTypeResourceService.findAll());
  }

  @GetMapping("/{id}")
  public OrganizerTypeResponse findById(@PathVariable Long id) {
    return organizerTypeMapper.toResponse(organizerTypeResourceService.findById(id));
  }
}
