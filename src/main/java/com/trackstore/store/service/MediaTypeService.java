package com.trackstore.store.service;

import java.util.List;

import com.trackstore.store.exception.StoreConstantViolationException;
import com.trackstore.store.exception.StoreResourceNotFoundException;
import com.trackstore.store.model.MediaType;
import org.springframework.stereotype.Service;

@Service
public interface MediaTypeService {

  MediaType createMediaType(MediaType mediaType);

  MediaType updateMediaType(MediaType mediaType, Long id) throws StoreResourceNotFoundException;

  MediaType getMediaType(Long id) throws StoreResourceNotFoundException;

  List<MediaType> getMediaTypes(String name);

  boolean deleteMediaType(Long id) throws StoreResourceNotFoundException, StoreConstantViolationException;

}
