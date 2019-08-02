package com.trackstore.store.service.impl;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Track;
import com.trackstore.store.repository.TrackRepository;
import com.trackstore.store.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TrackServiceImpl implements TrackService {

  @Autowired
  private TrackRepository trackRepository;

  @Override
  public Track createTrack(Track track) {
    Assert.notNull(track, "Entity must not be null!");
    return trackRepository.save(track);
  }

  @Override
  public Track updateTrack(Track track, Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Assert.notNull(track, "Entity must not be null!");
    if (!trackRepository.existsById(id)) {
      throw new StoreResourceNotFoundException("Track: " + id + " not found");
    }
    track.setTrackId(id);
    return trackRepository.save(track);
  }

  @Override
  public Track getTrack(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Track track = trackRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Track: " + id + " not found"));
    return track;
  }

  @Override
  public List<Track> getTracks(String name) {
    List<Track> tracks = trackRepository.findByNameContainsIgnoreCase(name);
    return tracks;
  }

  @Override
  public boolean deleteTrack(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Track track = trackRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Track: " + id + " not found"));
    trackRepository.delete(track);
    return !trackRepository.existsById(id);
  }

}
