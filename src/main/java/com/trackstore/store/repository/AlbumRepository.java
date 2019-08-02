package com.trackstore.store.repository;

import java.util.List;

import com.trackstore.store.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

  List<Album> findByTitleContainsIgnoreCase(String title);

}
