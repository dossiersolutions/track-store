package com.trackstore.store.controller;

import java.util.List;

import com.trackstore.store.exception.ResourceNotFoundException;
import com.trackstore.store.model.Album;
import com.trackstore.store.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
public class AlbumController {

  @Autowired
  private AlbumRepository albumRepository;

  @RequestMapping(value = "/albums", method = RequestMethod.GET)
  public ResponseEntity<Object> findAlbums(/*
      @RequestParam(name = "title", required = false, defaultValue = "") String title*/) {
    List<Album> albums = albumRepository.findAll();
    return new ResponseEntity<>(albums, HttpStatus.OK);
  }

}
