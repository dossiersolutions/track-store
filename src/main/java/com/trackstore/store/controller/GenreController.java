package com.trackstore.store.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonView;
import com.trackstore.store.StoreApplicationView;
import com.trackstore.store.constraint.APIPathConstrains;
import com.trackstore.store.model.Genre;
import com.trackstore.store.service.GenreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="Genre management system")
@RequestMapping(APIPathConstrains.ROOT_PATH)
@ExposesResourceFor(Genre.class)
@Produces(MediaType.APPLICATION_JSON)
public class GenreController {

  @Autowired
  private GenreService genreService;

  private static final Logger logger = LoggerFactory.getLogger(GenreController.class);

  @RequestMapping(value = APIPathConstrains.GENRE_PATH, method = RequestMethod.GET)
  @ApiOperation(value = "Find all genres",
      notes = "Also returns a link to retrieve specific Genre in details", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resources"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findGenres(
      @ApiParam(value = "Genre name") @RequestParam(name = "name", required = false, defaultValue = "") String name) {
    logger.info("Fetching genres with name {}", name);
    List<Genre> genres = genreService.getGenres(name);
    for(Genre genre: genres) {
      genre.add(linkTo(methodOn(GenreController.class).findGenre(genre.getGenreId())).withSelfRel());
    }
    return new ResponseEntity<>(genres, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.GENRE_PATH + "/{genreId}", method = RequestMethod.GET)
  @ApiOperation(value = "Find specific genre", response = Genre.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resource"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findGenre(@ApiParam(value = "Genre id") @PathVariable("genreId") Long id) {
    logger.info("Fetching genre with id {}", id);
    Genre genre = genreService.getGenre(id);
    genre.add(linkTo(methodOn(GenreController.class).findGenre(id)).withSelfRel());
    return new ResponseEntity<>(genre, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.GENRE_PATH, method = RequestMethod.POST)
  @ApiOperation(value = "Create new genre", response = Integer.class)
  public ResponseEntity<Object> createGenre(@Valid @RequestBody Genre genre) {
    logger.info("Creating genre {}", genre);
    Genre createdGenre = genreService.createGenre(genre);
    return ResponseEntity.ok(createdGenre);
  }

  @RequestMapping(value = APIPathConstrains.GENRE_PATH + "/{genreId}", method = RequestMethod.PUT)
  @ApiOperation(value = "Update genre", response = Genre.class)
  public ResponseEntity<Object> updateGenre(
      @ApiParam(value = "Genre id") @PathVariable("genreId") Long id, @Valid @RequestBody Genre genre) {
    logger.info("Updating genre with id {}", id);
    Genre updatedGenre = genreService.updateGenre(genre, id);
    return ResponseEntity.ok(updatedGenre);
  }

  @RequestMapping(value = APIPathConstrains.GENRE_PATH + "/{genreId}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete genre", response = Integer.class)
  public ResponseEntity<Object> deleteGenre(@PathVariable("genreId") Long id) {
    logger.info("Deleting genre with id {}", id);
    boolean isDeleted = genreService.deleteGenre(id);
    return ResponseEntity.ok(isDeleted ? 1 : 0);
  }

}
