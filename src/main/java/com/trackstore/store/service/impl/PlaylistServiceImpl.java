package com.trackstore.store.service.impl;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Playlist;
import com.trackstore.store.repository.PlaylistRepository;
import com.trackstore.store.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  @Autowired
  private PlaylistRepository playlistRepository;

  @Override
  public Playlist createPlaylist(Playlist playlist) {
    Assert.notNull(playlist, "Entity must not be null!");
    return playlistRepository.save(playlist);
  }

  @Override
  public Playlist updatePlaylist(Playlist playlist, Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Assert.notNull(playlist, "Entity must not be null!");
    if (!playlistRepository.existsById(id)) {
      throw new StoreResourceNotFoundException("Playlist: " + id + " not found");
    }
    playlist.setPlaylistId(id);
    return playlistRepository.save(playlist);
  }

  @Override
  public Playlist getPlaylist(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Playlist playlist = playlistRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Playlist: " + id + " not found"));
    return playlist;
  }

  @Override
  public List<Playlist> getPlaylists(String name) {
    List<Playlist> playlists = playlistRepository.findByNameContainsIgnoreCase(name);
    return playlists;
  }

  @Override
  public boolean deletePlaylist(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Playlist playlist = playlistRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Playlist: " + id + " not found"));
    playlistRepository.delete(playlist);
    return !playlistRepository.existsById(id);
  }

}
