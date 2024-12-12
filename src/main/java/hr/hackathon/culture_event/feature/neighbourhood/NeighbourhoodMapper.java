package hr.hackathon.culture_event.feature.neighbourhood;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NeighbourhoodMapper {

  NeighbourhoodResponse toResponse(Neighbourhood neighbourhood);
}
