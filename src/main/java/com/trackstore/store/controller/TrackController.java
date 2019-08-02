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
import com.trackstore.store.model.Track;
import com.trackstore.store.service.TrackService;
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
@ExposesResourceFor(Track.class)
@Produces(MediaType.APPLICATION_JSON)
public class TrackController {

  @Autowired
  private TrackService trackService;

  private static final Logger logger = LoggerFactory.getLogger(TrackController.class);

  @RequestMapping(value = APIPathConstrains.TRACK_PATH, method = RequestMethod.GET)
  @ApiOperation(value = "Find all tracks",
      notes = "Also returns a link to retrieve specific track in details", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resources"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findTracks(
      @ApiParam(value = "Track name") @RequestParam(name = "name", required = false, defaultValue = "") String name) {
    logger.info("Fetching tracks with name {}", name);
    List<Track> tracks = trackService.getTracks(name);
    for(Track track: tracks) {
      track.add(linkTo(methodOn(TrackController.class).findTrack(track.getTrackId())).withSelfRel());
    }
    return new ResponseEntity<>(tracks, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.TRACK_PATH + "/{trackId}", method = RequestMethod.GET)
  @ApiOperation(value = "Find specific track", response = Track.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resource"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  public ResponseEntity<Object> findTrack(@ApiParam(value = "Track id") @PathVariable("trackId") Long id) {
    logger.info("Fetching track with id {}", id);
    Track track = trackService.getTrack(id);
    track.add(linkTo(methodOn(TrackController.class).findTrack(id)).withSelfRel());
    return new ResponseEntity<>(track, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.TRACK_PATH, method = RequestMethod.POST)
  @ApiOperation(value = "Create new track", response = Integer.class)
  public ResponseEntity<Object> createTrack(@Valid @RequestBody Track track) {
    logger.info("Creating track {}", track);
    Track createdTrack = trackService.createTrack(track);
    return ResponseEntity.ok(createdTrack);
  }

  @RequestMapping(value = APIPathConstrains.TRACK_PATH + "/{trackId}", method = RequestMethod.PUT)
  @ApiOperation(value = "Update track", response = Track.class)
  public ResponseEntity<Object> updateTrack(
      @ApiParam(value = "Track id") @PathVariable("trackId") Long id,
      @Valid @RequestBody Track track) {
    logger.info("Updating track with id {}", id);
    Track updatedTrack = trackService.updateTrack(track, id);
    return ResponseEntity.ok(updatedTrack);
  }

  @RequestMapping(value = APIPathConstrains.TRACK_PATH + "/{trackId}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete track", response = Integer.class)
  public ResponseEntity<Object> deleteTrack(@PathVariable("trackId") Long id) {
    logger.info("Deleting track with id {}", id);
    boolean isDeleted = trackService.deleteTrack(id);
    return ResponseEntity.ok(isDeleted ? 1 : 0);
  }

}
