package com.trackstore.store.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.trackstore.store.StoreApplicationView;
import com.trackstore.store.constraint.DbTableConstrains;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = DbTableConstrains.ALBUM_TABLE_NAME)
@ApiModel(description="All details about the album.")
@JsonPropertyOrder({"albumId", "title", "tracks"})
public class Album extends AuditModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "album_id_sequence_generator")
  @SequenceGenerator(name="album_id_sequence_generator", sequenceName = "store_album_id_seq", allocationSize = 1)
  @Column(name = "id")
  @JsonProperty("albumId")
  @ApiModelProperty(notes = "The database generated album ID")
  @JsonView(StoreApplicationView.SimpleView.class)
  private Long albumId;

  @Column(name = "title")
  @JsonProperty("title")
  @ApiModelProperty(notes = "The album title")
  @JsonView(StoreApplicationView.SimpleView.class)
  private String title;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Artist.class, optional = false)
  @JoinColumn(name = "artist_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Artist artist;

  @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
  @JsonProperty("tracks")
  @ApiModelProperty(notes = "The album tracks")
  @JsonManagedReference
  @JsonView(StoreApplicationView.AlbumDetailedView.class)
  private List<Track> tracks;

  public Long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(Long albumId) {
    this.albumId = albumId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }
}
