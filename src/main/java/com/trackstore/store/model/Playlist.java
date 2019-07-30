package com.trackstore.store.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "playlist")
@JsonPropertyOrder({"playlistId", "name"})
public class Playlist /*extends AuditModel*/ {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @JsonProperty("playlistId")
  private Long id;

  @Column(name = "name")
  @JsonProperty("name")
  private String name;

  @ManyToMany
  @JoinTable(
      name = "playlist_track",
      joinColumns = @JoinColumn(name = "playlist_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id"))
  Set<Track> tracks;

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

}
