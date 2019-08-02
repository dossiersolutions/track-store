package com.trackstore.store.service.impl;

import java.util.List;

import com.trackstore.store.exception.StoreConstantViolationException;
import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.Genre;
import com.trackstore.store.model.Track;
import com.trackstore.store.repository.GenreRepository;
import com.trackstore.store.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GenreServiceImpl implements GenreService {

  @Autowired
  private GenreRepository genreRepository;

  @Override
  public Genre createGenre(Genre genre) {
    Assert.notNull(genre, "Entity must not be null!");
    return genreRepository.save(genre);
  }

  @Override
  public Genre updateGenre(Genre genre, Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Assert.notNull(genre, "Entity must not be null!");
    if (!genreRepository.existsById(id)) {
      throw new StoreResourceNotFoundException("Genre: " + id + " not found");
    }
    genre.setGenreId(id);
    return genreRepository.save(genre);
  }

  @Override
  public Genre getGenre(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Genre genre = genreRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("Genre: " + id + " not found"));
    return genre;
  }

  @Override
  public List<Genre> getGenres(String name) {
    List<Genre> genres = genreRepository.findByNameContainsIgnoreCase(name);
    return genres;
  }

  @Override
  public boolean deleteGenre(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Genre genre = genreRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException(String.format("Genre: %s not found", id)));
    List<Track> tracks = genre.getTracks();
    if (!tracks.isEmpty()) {
      throw new StoreConstantViolationException(String.format(
          "Cannot delete genre: %s (%s), there are tacks with this genre", id, genre.getName()));
    }
    genreRepository.delete(genre);
    return !genreRepository.existsById(id);
  }

}
