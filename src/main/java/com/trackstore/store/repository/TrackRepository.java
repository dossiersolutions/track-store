package com.trackstore.store.repository;

import java.util.List;

import com.trackstore.store.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

  List<Track> findByNameContainsIgnoreCase(String name);

}
