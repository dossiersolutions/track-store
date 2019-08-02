package com.trackstore.store.service;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Album;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {

  Album createAlbum(Album album);

  Album updateAlbum(Album album, Long id) throws StoreResourceNotFoundException;

  Album getAlbum(Long id) throws StoreResourceNotFoundException;

  List<Album> getAlbums(String name);

  boolean deleteAlbum(Long id) throws StoreResourceNotFoundException;

}
