package com.trackstore.store.service;

import java.util.List;
import java.util.Optional;

import com.trackstore.store.exception.ResourceNotFoundException;
import com.trackstore.store.model.Artist;
import org.springframework.stereotype.Service;

@Service
public interface ArtistService {

  void createArtist(Artist artist);

  void updateArtist(Long id, Artist artist) throws ResourceNotFoundException;

  Optional<Artist> getArtist(Long id) throws ResourceNotFoundException;

  List<Artist> getArtists(String name);

  void deleteArtist(Long id) throws ResourceNotFoundException;


}
