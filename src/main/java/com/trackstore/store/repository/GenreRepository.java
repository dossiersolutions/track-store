package com.trackstore.store.repository;

import java.util.List;

import com.trackstore.store.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

  List<Genre> findByNameContainsIgnoreCase(String name);

}
