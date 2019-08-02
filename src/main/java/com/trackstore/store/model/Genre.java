package com.trackstore.store.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trackstore.store.constraint.DbTableConstrains;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = DbTableConstrains.GENRE_TABLE_NAME)
@ApiModel(description="All details about the genre.")
@JsonPropertyOrder({"genreId", "name"})
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Genre extends AuditModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "genre_id_sequence_generator")
  @SequenceGenerator(name="genre_id_sequence_generator", sequenceName = "store_genre_id_seq", allocationSize = 1)
  @Column(name = "id")
  @ApiModelProperty(notes = "The database generated genre ID")
  @JsonProperty("genreId")
  private Long genreId;

  @Column(name = "name")
  @ApiModelProperty(notes = "The genre name")
  @JsonProperty("name")
  private String name;

  @OneToMany(mappedBy = "genre", cascade = CascadeType.PERSIST)
  @JsonIgnore
  private List<Track> tracks;

  public Long getGenreId() {
    return genreId;
  }

  public void setGenreId(Long genreId) {
    this.genreId = genreId;
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
