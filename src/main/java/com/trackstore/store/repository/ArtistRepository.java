package com.trackstore.store.repository;

import java.util.List;

import com.trackstore.store.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

  List<Artist> findByNameContainsIgnoreCase(String name);

}
