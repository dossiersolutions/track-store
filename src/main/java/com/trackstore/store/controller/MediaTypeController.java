package com.trackstore.store.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.annotation.JsonView;
import com.trackstore.store.StoreApplicationView;
import com.trackstore.store.constraint.APIPathConstrains;
import com.trackstore.store.model.MediaType;
import com.trackstore.store.service.MediaTypeService;
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
@Api(value="Media type management system")
@RequestMapping(APIPathConstrains.ROOT_PATH)
@ExposesResourceFor(MediaType.class)
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class MediaTypeController {

  @Autowired
  private MediaTypeService mediaTypeService;

  private static final Logger logger = LoggerFactory.getLogger(MediaTypeController.class);

  @RequestMapping(value = APIPathConstrains.MEDIA_TYPE_PATH, method = RequestMethod.GET)
  @ApiOperation(value = "Find all tracks",
      notes = "Also returns a link to retrieve specific media type in details", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resources"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findMediaTypes(
      @ApiParam(value = "Media type name") @RequestParam(name = "name", required = false, defaultValue = "") String name) {
    logger.info("Fetching media types with name {}", name);
    List<MediaType> mediaTypes = mediaTypeService.getMediaTypes(name);
    for(MediaType mediaType: mediaTypes) {
      mediaType.add(linkTo(methodOn(MediaTypeController.class).findMediaType(mediaType.getMediaTypeId())).withSelfRel());
    }
    return new ResponseEntity<>(mediaTypes, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.MEDIA_TYPE_PATH + "/{mediaTypeId}", method = RequestMethod.GET)
  @ApiOperation(value = "Find specific media type", response = MediaType.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved resource"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @JsonView(StoreApplicationView.SimpleView.class)
  public ResponseEntity<Object> findMediaType(@ApiParam(value = "Media type id") @PathVariable("mediaTypeId") Long id) {
    logger.info("Fetching media type with id {}", id);
    MediaType mediaType = mediaTypeService.getMediaType(id);
    mediaType.add(linkTo(methodOn(MediaTypeController.class).findMediaType(id)).withSelfRel());
    return new ResponseEntity<>(mediaType, HttpStatus.OK);
  }

  @RequestMapping(value = APIPathConstrains.MEDIA_TYPE_PATH, method = RequestMethod.POST)
  @ApiOperation(value = "Create new media type", response = Integer.class)
  public ResponseEntity<Object> createMediaType(@Valid @RequestBody MediaType mediaType) {
    logger.info("Creating media type {}", mediaType);
    MediaType createdMediaType = mediaTypeService.createMediaType(mediaType);
    return ResponseEntity.ok(createdMediaType);
  }

  @RequestMapping(value = APIPathConstrains.MEDIA_TYPE_PATH + "/{mediaTypeId}", method = RequestMethod.PUT)
  @ApiOperation(value = "Update media type", response = MediaType.class)
  public ResponseEntity<Object> updateMediaType(
      @ApiParam(value = "Media type id") @PathVariable("mediaTypeId") Long id,
      @Valid @RequestBody MediaType mediaType) {
    logger.info("Updating media type with id {}", id);
    MediaType updatedMediaType = mediaTypeService.updateMediaType(mediaType, id);
    return ResponseEntity.ok(updatedMediaType);
  }

  @RequestMapping(value = APIPathConstrains.MEDIA_TYPE_PATH + "/{mediaTypeId}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete media type", response = Integer.class)
  public ResponseEntity<Object> deleteMediaType(@PathVariable("mediaTypeId") Long id) {
    logger.info("Deleting media type with id {}", id);
    boolean isDeleted = mediaTypeService.deleteMediaType(id);
    return ResponseEntity.ok(isDeleted ? 1 : 0);
  }

}
