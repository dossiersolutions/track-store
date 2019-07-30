package com.trackstore.store.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "track")
@JsonPropertyOrder({"trackId", "name", "composer", "album", "mediaType", "genre", "milliseconds", "bytes"})
public class Track /*extends AuditModel*/ {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @JsonProperty("playlistId")
  private Long id;

  @Column(name = "name")
  @JsonProperty("name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "album_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  @JsonProperty("album")
  private Album album;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "media_type_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  @JsonProperty("mediaType")
  private MediaType mediaType;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "genre_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  @JsonProperty("genre")
  private Genre genre;

  @ManyToMany
  @JoinTable(
      name = "playlist_track",
      joinColumns = @JoinColumn(name = "track_id"),
      inverseJoinColumns = @JoinColumn(name = "playlist_id"))
  Set<Playlist> playlists;

  @Column(name = "composer")
  @JsonProperty("composer")
  private String composer;

  @Column(name = "milliseconds")
  @JsonProperty("milliseconds")
  private Long milliseconds;

  @Column(name = "bytes")
  @JsonProperty("bytes")
  private Long bytes;

  @Column(name = "unitPrice")
  @JsonProperty("unit_price")
  private Long unitPrice;

}
