package com.trackstore.store.service;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Artist;
import org.springframework.stereotype.Service;

@Service
public interface ArtistService {

  Artist createArtist(Artist artist);

  Artist updateArtist(Artist artist, Long id) throws StoreResourceNotFoundException;

  Artist getArtist(Long id) throws StoreResourceNotFoundException;

  List<Artist> getArtists(String name);

  boolean deleteArtist(Long id) throws StoreResourceNotFoundException;

}
