package com.trackstore.store.service.impl;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Artist;
import com.trackstore.store.repository.ArtistRepository;
import com.trackstore.store.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ArtistServiceImpl implements ArtistService {

  @Autowired
  private ArtistRepository artistRepository;

  @Override
  public Artist createArtist(Artist artist) {
    Assert.notNull(artist, "Entity must not be null!");
    return artistRepository.save(artist);
  }

  @Override
  public Artist updateArtist(Artist artist, Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Assert.notNull(artist, "Entity must not be null!");
    if (!artistRepository.existsById(id)) {
      throw new StoreResourceNotFoundException("Artist: " + id + " not found");
    }
    artist.setArtistId(id);
    return artistRepository.save(artist);
  }

  @Override
  public Artist getArtist(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Artist artist = artistRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Artist: " + id + " not found"));
    return artist;
  }

  @Override
  public List<Artist> getArtists(String name) {
    List<Artist> artists = artistRepository.findByNameContainsIgnoreCase(name);
    return artists;
  }

  @Override
  public boolean deleteArtist(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Artist artist = artistRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Artist: " + id + " not found"));
    artistRepository.delete(artist);
    return !artistRepository.existsById(id);
  }

}
