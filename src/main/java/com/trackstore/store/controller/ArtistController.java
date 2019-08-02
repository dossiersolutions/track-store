package com.trackstore.store.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonView;
import com.trackstore.store.constraint.APIPathConstrains;
import com.trackstore.store.StoreApplicationView;
import com.trackstore.store.model.Album;
import com.trackstore.store.model.Artist;
import com.trackstore.store.service.ArtistService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value="Artist management system")
@RequestMapping(APIPathConstrains.ROOT_PATH)
@ExposesResourceFor(Artist.class)
@Produces(MediaType.APPLICATION_JSON)
public class ArtistController {

  @Autowired
  private ArtistService artistService;

  private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

  @RequestMapping(value = APIPathConstrains.ARTIST_PATH, method = RequestMethod.GET)
  @ApiOperation(value = "Find all artists",
      notes = "Also returns a link to retrieve specific artist in details", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resources"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findArtists(
      @ApiParam(value = "Artist name") @RequestParam(name = "name", required = false, defaultValue = "") String name) {
    logger.info("Fetching artists with name {}", name);
    List<Artist> artists = artistService.getArtists(name);
    for(Artist artist: artists) {
      artist.add(linkTo(methodOn(ArtistController.class).findArtist(artist.getArtistId())).withSelfRel());
    }
    return new ResponseEntity<>(artists, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.ARTIST_PATH + "/{artistId}", method = RequestMethod.GET)
  @ApiOperation(value = "Find specific artists", response = Artist.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resource"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.ArtistDetailedView.class)
  public ResponseEntity<Object> findArtist(@ApiParam(value = "Artist id") @PathVariable("artistId") Long id) {
    logger.info("Fetching artist with id {}", id);
    Artist artist = artistService.getArtist(id);
    artist.add(linkTo(methodOn(ArtistController.class).findArtist(id)).withSelfRel());
    for (Album album: artist.getAlbums()) {
      album.add(linkTo(methodOn(AlbumController.class).findAlbum(album.getAlbumId())).withSelfRel());
    }
    return new ResponseEntity<>(artist, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.ARTIST_PATH, method = RequestMethod.POST)
  @ApiOperation(value = "Create new artist", response = Artist.class)
  public ResponseEntity<Object> createArtist(@Valid @RequestBody Artist artist) {
    logger.info("Creating artist {}", artist);
    Artist createdArtist = artistService.createArtist(artist);
    return ResponseEntity.ok(createdArtist);
  }

  @RequestMapping(value = APIPathConstrains.ARTIST_PATH + "/{artistId}", method = RequestMethod.PUT)
  @ApiOperation(value = "Update artist", response = Artist.class)
  public ResponseEntity<Object> updateAlbum(
      @ApiParam(value = "Artist id") @PathVariable("artistId") Long id, @Valid @RequestBody Artist artist) {
    logger.info("Updating artist with id {}", id);
    Artist updatedArtist = artistService.updateArtist(artist, id);
    return ResponseEntity.ok(updatedArtist);
  }

  @RequestMapping(value = APIPathConstrains.ARTIST_PATH + "/{artistId}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete artist", response = Integer.class)
  public ResponseEntity<Object> deleteArtist(@PathVariable("artistId") Long id) {
    logger.info("Deleting artist with id {}", id);
    boolean isDeleted = artistService.deleteArtist(id);
    return ResponseEntity.ok(isDeleted ? 1 : 0);
  }

}
