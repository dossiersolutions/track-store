package com.trackstore.store.service;

import java.util.List;

import com.trackstore.store.exception.StoreConstantViolationException;
import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Genre;
import org.springframework.stereotype.Service;

@Service
public interface GenreService {

  Genre createGenre(Genre genre);

  Genre updateGenre(Genre genre, Long id) throws StoreResourceNotFoundException;

  Genre getGenre(Long id) throws StoreResourceNotFoundException;

  List<Genre> getGenres(String name);

  boolean deleteGenre(Long id) throws StoreResourceNotFoundException, StoreConstantViolationException;

}
