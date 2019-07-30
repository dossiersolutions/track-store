package com.trackstore.store.controller;

import java.util.List;
import java.util.Optional;

import com.trackstore.store.exception.ResourceNotFoundException;
import com.trackstore.store.model.Artist;
import com.trackstore.store.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
public class ArtistController {

  @Autowired
  private ArtistService artistService;

  @RequestMapping(value = "/artists", method = RequestMethod.GET)
  public ResponseEntity<Object> findArtists(
      @RequestParam(name = "name", required = false, defaultValue = "") String name) {
    List<Artist> artists = artistService.getArtists(name);
    return new ResponseEntity<>(artists, HttpStatus.OK);
  }

  @RequestMapping(value = "/artists/{artistId}", method = RequestMethod.GET)
  public ResponseEntity<Object> findArtist(@PathVariable("artistId") Long id) {
    Optional<Artist> artist = artistService.getArtist(id);
    if (!artist.isPresent()) {
      throw new ResourceNotFoundException("Artist: " + id + " not found");
    }
    return new ResponseEntity<>(artist, HttpStatus.OK);
  }

  @RequestMapping(value = "/artists/{artistId}", method = RequestMethod.PUT)
  public ResponseEntity<Object> updateArtist(@PathVariable("artistId") Long id, @RequestBody Artist artist) {
    artistService.updateArtist(id, artist);
    return new ResponseEntity<>('1', HttpStatus.OK);
  }

  @RequestMapping(value = "/artists/{artistId}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> deleteArtist(@PathVariable("artistId") Long id) {
    Optional<Artist> artist = artistService.getArtist(id);
    if (!artist.isPresent()) {
      throw new ResourceNotFoundException("Artist: " + id + " not found");
    }
    artistService.deleteArtist(id);
    return new ResponseEntity<>('1', HttpStatus.OK);
  }

}
