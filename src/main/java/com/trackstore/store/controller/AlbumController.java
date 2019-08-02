package com.trackstore.store.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonView;
import com.trackstore.store.constraint.APIPathConstrains;
import com.trackstore.store.StoreApplicationView;
import com.trackstore.store.model.Album;
import com.trackstore.store.model.Track;
import com.trackstore.store.service.AlbumService;
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
@Api(value="Album management system")
@RequestMapping(APIPathConstrains.ROOT_PATH)
@ExposesResourceFor(Album.class)
@Produces(MediaType.APPLICATION_JSON)
public class AlbumController {

  @Autowired
  private AlbumService albumService;

  private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

  @RequestMapping(value = APIPathConstrains.ALBUM_PATH, method = RequestMethod.GET)
  @JsonView(StoreApplicationView.SimpleView.class)
  @ApiOperation(value = "Find all albums",
      notes = "Also returns a link to retrieve specific album in details", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resources"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  public ResponseEntity<Object> findAlbums(
      @ApiParam(value = "Album name") @RequestParam(name = "title", required = false, defaultValue = "") String title) {
    logger.info("Fetching albums with title {}", title);
    List<Album> albums = albumService.getAlbums(title);
    for (Album album: albums) {
      album.add(linkTo(methodOn(AlbumController.class).findAlbum(album.getAlbumId())).withSelfRel());
    }
    return new ResponseEntity<>(albums, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.ALBUM_PATH + "/{albumId}", method = RequestMethod.GET)
  @ApiOperation(value = "Find specific album", response = Album.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resource"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.AlbumDetailedView.class)
  public ResponseEntity<Object> findAlbum(@ApiParam(value = "Album id") @PathVariable("albumId") Long id) {
    logger.info("Fetching album with id {}", id);
    Album album = albumService.getAlbum(id);
    album.add(linkTo(methodOn(AlbumController.class).findAlbum(id)).withSelfRel());
    for (Track track: album.getTracks()) {
      track.add(linkTo(methodOn(TrackController.class).findTrack(track.getTrackId())).withSelfRel());
    }
    return new ResponseEntity<>(album, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.ALBUM_PATH, method = RequestMethod.POST)
  @ApiOperation(value = "Create new album", response = Album.class)
  public ResponseEntity<Object> createAlbum(@Valid @RequestBody Album album) {
    logger.info("Creating album {}", album);
    Album createdAlbum = albumService.createAlbum(album);
    return ResponseEntity.ok(createdAlbum);
  }

  @RequestMapping(value = APIPathConstrains.ALBUM_PATH + "/{albumId}", method = RequestMethod.PUT)
  @ApiOperation(value = "Update album", response = Album.class)
  public ResponseEntity<Object> updateAlbum(
      @ApiParam(value = "Album id") @PathVariable("albumId") Long id,
      @Valid @RequestBody Album album) {
    logger.info("Updating album with id {}", id);
    Album updatedAlbum = albumService.updateAlbum(album, id);
    return ResponseEntity.ok(updatedAlbum);
  }

  @RequestMapping(value = APIPathConstrains.ALBUM_PATH + "/{albumId}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete album", response = Integer.class)
  public ResponseEntity<Object> deleteAlbum(@PathVariable("albumId") Long id) {
    logger.info("Deleting album with id {}", id);
    boolean isDeleted = albumService.deleteAlbum(id);
    return ResponseEntity.ok(isDeleted ? 1 : 0);
  }

}
