package com.trackstore.store.service;

import java.util.List;

import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Track;
import org.springframework.stereotype.Service;

@Service
public interface TrackService {

  Track createTrack(Track track);

  Track updateTrack(Track track, Long id) throws StoreResourceNotFoundException;

  Track getTrack(Long id) throws StoreResourceNotFoundException;

  List<Track> getTracks(String name);

  boolean deleteTrack(Long id) throws StoreResourceNotFoundException;

}
