package lt.techin.movie_studio.controller;

import jakarta.validation.Valid;
import lt.techin.movie_studio.dto.ActorDTO;
import lt.techin.movie_studio.dto.ActorMapper;
import lt.techin.movie_studio.dto.MovieMapper;
import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ActorController {

  private final ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("/actors")
  public ResponseEntity<List<ActorDTO>> getActors() {
    return ResponseEntity.ok(ActorMapper.toActorDTOList(actorService.findAllActors()));
  }

  @GetMapping("/actors/{id}")
  public ResponseEntity<ActorDTO> getActor(@PathVariable long id) {
    Optional<Actor> foundActor = actorService.findActorById(id);

    if (foundActor.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(ActorMapper.toActorDTO(foundActor.get()));
  }

  @PostMapping("/actors")
  public ResponseEntity<?> addActor(@Valid @RequestBody ActorDTO actorDTO) {

    Actor savedActor = actorService.saveActor(ActorMapper.toActor(actorDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedActor.getId()
                    )
                    .toUri())
            .body(ActorMapper.toActorDTO(savedActor));

  }


}
