package com.trackstore.store.service;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Playlist;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistService {

  Playlist createPlaylist(Playlist playlist);

  Playlist updatePlaylist(Playlist playlist, Long id) throws StoreResourceNotFoundException;

  Playlist getPlaylist(Long id) throws StoreResourceNotFoundException;

  List<Playlist> getPlaylists(String name);

  boolean deletePlaylist(Long id) throws StoreResourceNotFoundException;

}
