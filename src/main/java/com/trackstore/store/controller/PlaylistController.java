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
import com.trackstore.store.model.Playlist;
import com.trackstore.store.service.PlaylistService;
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
@Api(value="Track management system")
@RequestMapping(APIPathConstrains.ROOT_PATH)
@ExposesResourceFor(Playlist.class)
@Produces(MediaType.APPLICATION_JSON)
public class PlaylistController {

  @Autowired
  private PlaylistService playlistService;

  private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);

  @RequestMapping(value = APIPathConstrains.PLAYLISTS_PATH, method = RequestMethod.GET)
  @ApiOperation(value = "Find all tracks",
      notes = "Also returns a link to retrieve specific track in details", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resources"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findPlaylists(
      @ApiParam(value = "Playlist name") @RequestParam(name = "name", required = false, defaultValue = "") String name) {
    logger.info("Fetching playlists with name {}", name);
    List<Playlist> playlists = playlistService.getPlaylists(name);
    for(Playlist playlist: playlists) {
      playlist.add(linkTo(methodOn(PlaylistController.class).findPlaylist(playlist.getPlaylistId())).withSelfRel());
    }
    return new ResponseEntity<>(playlists, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.PLAYLISTS_PATH + "/{playlistId}", method = RequestMethod.GET)
  @ApiOperation(value = "Find specific playlist", response = Playlist.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resource"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findPlaylist(@ApiParam(value = "Playlist id") @PathVariable("playlistId") Long id) {
    logger.info("Fetching playlist with id {}", id);
    Playlist playlist = playlistService.getPlaylist(id);
    playlist.add(linkTo(methodOn(PlaylistController.class).findPlaylist(id)).withSelfRel());
    return new ResponseEntity<>(playlist, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.PLAYLISTS_PATH, method = RequestMethod.POST)
  @ApiOperation(value = "Create new playlist", response = Integer.class)
  public ResponseEntity<Object> createPlaylist(@Valid @RequestBody Playlist playlist) {
    logger.info("Creating playlist {}", playlist);
    Playlist createdPlaylist = playlistService.createPlaylist(playlist);
    return ResponseEntity.ok(createdPlaylist);
  }

  @RequestMapping(value = APIPathConstrains.PLAYLISTS_PATH + "/{playlistId}", method = RequestMethod.PUT)
  @ApiOperation(value = "Update playlist", response = MediaType.class)
  public ResponseEntity<Object> updatePlaylist(
      @ApiParam(value = "Playlist id") @PathVariable("playlistId") Long id,
      @Valid @RequestBody Playlist playlist) {
    logger.info("Updating playlist with id {}", id);
    Playlist updatedPlaylist = playlistService.updatePlaylist(playlist, id);
    return ResponseEntity.ok(updatedPlaylist);
  }

  @RequestMapping(value = APIPathConstrains.PLAYLISTS_PATH + "/{playlistId}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete playlist", response = Integer.class)
  public ResponseEntity<Object> deletePlaylist(@PathVariable("playlistId") Long id) {
    logger.info("Deleting playlist with id {}", id);
    boolean isDeleted = playlistService.deletePlaylist(id);
    return ResponseEntity.ok(isDeleted ? 1 : 0);
  }

}
