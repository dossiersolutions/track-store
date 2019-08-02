package com.trackstore.store.repository;

import java.util.List;

import com.trackstore.store.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

  List<Playlist> findByNameContainsIgnoreCase(String name);

}
