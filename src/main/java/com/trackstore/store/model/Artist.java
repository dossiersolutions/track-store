package com.trackstore.store.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.trackstore.store.StoreApplicationView;
import com.trackstore.store.constraint.DbTableConstrains;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = DbTableConstrains.ARTIST_TABLE_NAME)
@ApiModel(description="All details about the artist.")
@JsonPropertyOrder({"artistId", "name", "albums"})
public class Artist extends AuditModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "artist_id_sequence_generator")
  @SequenceGenerator(name="artist_id_sequence_generator", sequenceName = "store_artist_id_seq", allocationSize = 1)
  @Column(name = "id")
  @JsonProperty("artistId")
  @ApiModelProperty(notes = "The database generated artist ID")
  @JsonView(StoreApplicationView.SimpleView.class)
  private Long artistId;

  @Column(name = "name")
  @JsonProperty("name")
  @ApiModelProperty(notes = "The artist name")
  @JsonView(StoreApplicationView.SimpleView.class)
  private String name;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
  @JsonProperty("albums")
  @ApiModelProperty(notes = "The artist albums")
  @JsonManagedReference
  @JsonView(StoreApplicationView.ArtistDetailedView.class)
  private List<Album> albums;

  public Long getArtistId() {
    return artistId;
  }

  public void setArtistId(Long artistId) {
    this.artistId = artistId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(List<Album> albums) {
    this.albums = albums;
  }

}
