package com.trackstore.store.service.impl;

import java.util.List;
import java.util.Optional;

import com.trackstore.store.exception.ResourceNotFoundException;
import com.trackstore.store.model.Artist;
import com.trackstore.store.repository.ArtistRepository;
import com.trackstore.store.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl implements ArtistService {

  @Autowired
  private ArtistRepository artistRepository;

  @Override
  public void createArtist(Artist artist) {

  }

  @Override
  public void updateArtist(Long id, Artist artist) throws ResourceNotFoundException {

  }

  @Override
  public Optional<Artist> getArtist(Long id) throws ResourceNotFoundException {
    return artistRepository.findById(id);
  }

  @Override
  public List<Artist> getArtists(String name) {
    List<Artist> artists = artistRepository.findByNameContainsIgnoreCase(name);
    return artists;
  }

  @Override
  public void deleteArtist(Long id) throws ResourceNotFoundException {

  }

}
