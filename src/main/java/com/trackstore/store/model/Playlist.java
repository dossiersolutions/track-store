package com.trackstore.store.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trackstore.store.constraint.DbTableConstrains;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = DbTableConstrains.PLAYLIST_TABLE_NAME)
@ApiModel(description="All details about the playlist.")
@JsonPropertyOrder({"playlistId", "name"})
public class Playlist extends AuditModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "playlist_id_sequence_generator")
  @SequenceGenerator(name="playlist_id_sequence_generator", sequenceName = "store_playlist_id_seq", allocationSize = 1)
  @Column(name = "id")
  @JsonProperty("playlistId")
  @ApiModelProperty(notes = "The database generated playlist ID")
  private Long playlistId;

  @Column(name = "name")
  @ApiModelProperty(notes = "The playlist name")
  @JsonProperty("name")
  private String name;

  @ManyToMany
  @JoinTable(
      name = "playlist_track",
      joinColumns = @JoinColumn(name = "playlist_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id"))
  @JsonIgnore
  private List<Track> tracks;

  public Long getPlaylistId() {
    return playlistId;
  }

  public void setPlaylistId(Long playlistId) {
    this.playlistId = playlistId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }
}
