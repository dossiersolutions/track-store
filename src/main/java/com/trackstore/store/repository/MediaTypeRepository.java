package com.trackstore.store.repository;

import java.util.List;

import com.trackstore.store.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Long> {

  List<MediaType> findByNameContainsIgnoreCase(String name);

}
