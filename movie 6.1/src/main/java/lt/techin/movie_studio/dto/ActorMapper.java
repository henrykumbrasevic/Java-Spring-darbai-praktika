package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Actor;

import java.util.List;

public class ActorMapper {


  public static Actor toActor(ActorDTO actorDTO) {
    Actor actor = new Actor();
    actor.setName(actorDTO.name());
    actor.setSurname(actorDTO.surname());
    actor.setAge(actorDTO.age());
    return actor;
  }

  public static List<ActorDTO> toActorDTOList(List<Actor> actors) {
    List<ActorDTO> result = actors.stream().map(actor -> new ActorDTO(actor.getId(), actor.getName(), actor.getSurname(), actor.getAge())).toList();
    return result;
  }

  public static ActorDTO toActorDTO(Actor actor) {
    return new ActorDTO(actor.getId(), actor.getName(), actor.getSurname(), actor.getAge());

  }
}
