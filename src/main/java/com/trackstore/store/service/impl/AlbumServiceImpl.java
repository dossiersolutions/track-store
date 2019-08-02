package com.trackstore.store.service.impl;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Album;
import com.trackstore.store.repository.AlbumRepository;
import com.trackstore.store.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AlbumServiceImpl implements AlbumService {

  @Autowired
  private AlbumRepository albumRepository;

  @Override
  public Album createAlbum(Album album) {
    Assert.notNull(album, "Entity must not be null!");
    return albumRepository.save(album);
  }

  @Override
  public Album updateAlbum(Album album, Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Assert.notNull(album, "Entity must not be null!");
    if (!albumRepository.existsById(id)) {
      throw new StoreResourceNotFoundException("Album: " + id + " not found");
    }
    album.setAlbumId(id);
    return albumRepository.save(album);
  }

  @Override
  public Album getAlbum(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Album album = albumRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Album: " + id + " not found"));
    return album;
  }

  @Override
  public List<Album> getAlbums(String name) {
    List<Album> albums = albumRepository.findByTitleContainsIgnoreCase(name);
    return albums;
  }

  @Override
  public boolean deleteAlbum(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Album album = albumRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Album: " + id + " not found"));
    albumRepository.delete(album);
    return !albumRepository.existsById(id);
  }

}
