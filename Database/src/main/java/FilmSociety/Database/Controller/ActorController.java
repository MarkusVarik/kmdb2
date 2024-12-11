package FilmSociety.Database.Controller;

import FilmSociety.Database.Entity.Actor;
import FilmSociety.Database.Service.ActorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/actors") //Base URL for all endpoints in this controller
public class ActorController {

    @Autowired
    private ActorService actorService;

    //Endpoint to create an actor
    @PostMapping
    public ResponseEntity<?> createActor(@Valid @RequestBody Actor actor) {
            Actor createdActor = actorService.createActor(actor);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdActor); //Specifying the http status to fit the task requirements
    }

    //Endpoint to get all actors with pagination
    @GetMapping
    public ResponseEntity<?> getAllActors(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        //Validate page and size parameters
        if (page < 0) {
            throw new IllegalStateException("Page number must be greater than or equal to 0");
        }
        if (size > 100) {
            throw new IllegalStateException("Page size must be less than or equal to 100");
        }
        //Fetch the actors using a service method and construct the response manually so it looks better
        Page<Actor> actorPage = actorService.getAllActors(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("content", actorPage.getContent());
        response.put("totalElements", actorPage.getTotalElements());
        response.put("totalPages", actorPage.getTotalPages());
        response.put("elementsOnThisPage", actorPage.getNumberOfElements());

        return ResponseEntity.ok(response);
    }

    //Endpoint to get an actor by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getActorById(@PathVariable Long id) {
            return ResponseEntity.ok(actorService.getActorById(id));
    }

    //Endpoint to update an existing actor
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateActor(@PathVariable Long id,
                                         @RequestBody Map<String, Object> updatedActor) {
                return ResponseEntity.ok(actorService.updateActor(id, updatedActor));
    }

    //Endpoint to delete an actor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActorById(@PathVariable Long id,
                                             @RequestParam(defaultValue = "false") boolean force) {
        actorService.deleteActor(id, force);
        return ResponseEntity.noContent().build();
    }

    //Endpoint to search for actors by name
    @GetMapping("/search")
    public ResponseEntity<?> getActorByName(@RequestParam String name,
                                            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
                                            @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size) {
        if (page < 0) {
            throw new IllegalStateException("Page number must be greater than or equal to 0");
        }
        if (size > 100) {
            throw new IllegalStateException("Page size must be less than or equal to 100");
        }
        //Search for actors using a service method and construct the response
        Page<Actor> actorPage = actorService.findByName(name, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", actorPage.getContent());
        response.put("totalElements", actorPage.getTotalElements());
        response.put("totalPages", actorPage.getTotalPages());
        response.put("elementsOnThisPage", actorPage.getNumberOfElements());

        return ResponseEntity.ok(response);
    }
}
