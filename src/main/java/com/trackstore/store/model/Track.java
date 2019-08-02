package com.trackstore.store.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = DbTableConstrains.TRACK_TABLE_NAME)
@ApiModel(description="All details about the track.")
@JsonPropertyOrder({"trackId", "name", "composer", "mediaType", "genre", "milliseconds", "bytes", "unitPrice"})
public class Track extends AuditModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "track_id_sequence_generator")
  @SequenceGenerator(name="track_id_sequence_generator", sequenceName = "store_track_id_seq", allocationSize = 1)
  @Column(name = "id")
  @JsonProperty("trackId")
  @ApiModelProperty(notes = "The database generated track ID")
  @JsonView(StoreApplicationView.SimpleView.class)
  private Long trackId;

  @Column(name = "name")
  @JsonProperty("name")
  @ApiModelProperty(notes = "Track name")
  @JsonView(StoreApplicationView.SimpleView.class)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Album.class, optional = false)
  @JoinColumn(name = "album_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Album album;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = MediaType.class, optional = false)
  @JoinColumn(name = "media_type_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  @JsonProperty("mediaType")
  @ApiModelProperty(notes = "Track media type")
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  private MediaType mediaType;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Genre.class, optional = false)
  @JoinColumn(name = "genre_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  @JsonProperty("genre")
  @ApiModelProperty(notes = "Track genre")
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  private Genre genre;

  @ManyToMany
  @JoinTable(
      name = "playlist_track",
      joinColumns = @JoinColumn(name = "track_id"),
      inverseJoinColumns = @JoinColumn(name = "playlist_id"))
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  private List<Playlist> playlists;

  @Column(name = "composer")
  @JsonProperty("composer")
  @ApiModelProperty(notes = "Track composer")
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  private String composer;

  @Column(name = "milliseconds")
  @JsonProperty("milliseconds")
  @ApiModelProperty(notes = "Track length in milliseconds (ms)")
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  private Long milliseconds;

  @Column(name = "bytes")
  @JsonProperty("bytes")
  @ApiModelProperty(notes = "Track size in bytes (b)")
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  private Long bytes;

  @Column(name = "unit_price")
  @JsonProperty("unitPrice")
  @ApiModelProperty(notes = "Track unit price in dollars ($)")
  @JsonView(StoreApplicationView.TrackDetailedView.class)
  private Float unitPrice;

  public Long getTrackId() {
    return trackId;
  }

  public void setTrackId(Long trackId) {
    this.trackId = trackId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public List<Playlist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }

  public String getComposer() {
    return composer;
  }

  public void setComposer(String composer) {
    this.composer = composer;
  }

  public Long getMilliseconds() {
    return milliseconds;
  }

  public void setMilliseconds(Long milliseconds) {
    this.milliseconds = milliseconds;
  }

  public Long getBytes() {
    return bytes;
  }

  public void setBytes(Long bytes) {
    this.bytes = bytes;
  }

  public Float getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Float unitPrice) {
    this.unitPrice = unitPrice;
  }
}
