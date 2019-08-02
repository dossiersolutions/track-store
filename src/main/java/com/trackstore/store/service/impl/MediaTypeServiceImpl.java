package com.trackstore.store.service.impl;

import java.util.List;

import com.trackstore.store.exception.StoreConstantViolationException;
import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.MediaType;
import com.trackstore.store.model.Track;
import com.trackstore.store.repository.MediaTypeRepository;
import com.trackstore.store.service.MediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MediaTypeServiceImpl implements MediaTypeService {

  @Autowired
  private MediaTypeRepository mediaTypeRepository;

  @Override
  public MediaType createMediaType(MediaType mediaType) {
    Assert.notNull(mediaType, "Entity must not be null!");
    return mediaTypeRepository.save(mediaType);
  }

  @Override
  public MediaType updateMediaType(MediaType mediaType, Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    Assert.notNull(mediaType, "Entity must not be null!");
    if (!mediaTypeRepository.existsById(id)) {
      throw new StoreResourceNotFoundException("MediaType: " + id + " not found");
    }
    mediaType.setMediaTypeId(id);
    return mediaTypeRepository.save(mediaType);
  }

  @Override
  public MediaType getMediaType(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    MediaType mediaType = mediaTypeRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException("MediaType: " + id + " not found"));
    return mediaType;
  }

  @Override
  public List<MediaType> getMediaTypes(String name) {
    List<MediaType> mediaTypes = mediaTypeRepository.findByNameContainsIgnoreCase(name);
    return mediaTypes;
  }

  @Override
  public boolean deleteMediaType(Long id) throws StoreResourceNotFoundException {
    Assert.notNull(id, "Id must not be null!");
    MediaType mediaType = mediaTypeRepository.findById(id)
        .orElseThrow(() -> new StoreResourceNotFoundException(String.format("MediaType: %s not found", id)));
    List<Track> tracks = mediaType.getTracks();
    if (!tracks.isEmpty()) {
      throw new StoreConstantViolationException(String.format(
          "Cannot delete media type: %s (%s), there are tacks with this media type", id, mediaType.getName()));
    }
    mediaTypeRepository.delete(mediaType);
    return !mediaTypeRepository.existsById(id);
  }

}
