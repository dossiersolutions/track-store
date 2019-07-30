package com.trackstore.store.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "artist")
@JsonPropertyOrder({"artistId", "name", "albums"})
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @JsonProperty("artistId")
  private Long id;

  @Column(name = "name")
  @JsonProperty("name")
  private String name;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
  @JsonProperty("albums")
  private Set<Album> albums;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(Set<Album> albums) {
    this.albums = albums;
  }
}
