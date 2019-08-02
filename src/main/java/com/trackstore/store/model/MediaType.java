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
@Table(name = DbTableConstrains.MEDIA_TYPE_TABLE_NAME)
@ApiModel(description="All details about the media type.")
@JsonPropertyOrder({"mediaTypeId", "name"})
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class MediaType extends AuditModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "media_type_id_sequence_generator")
  @SequenceGenerator(name="media_type_id_sequence_generator", sequenceName = "store_media_type_id_seq", allocationSize = 1)
  @Column(name = "id")
  @ApiModelProperty(notes = "The database generated media type ID")
  @JsonProperty("mediaTypeId")
  private Long mediaTypeId;

  @Column(name = "name")
  @ApiModelProperty(notes = "The media type name")
  @JsonProperty("name")
  private String name;

  @OneToMany(mappedBy = "mediaType", cascade = CascadeType.PERSIST)
  @JsonIgnore
  private List<Track> tracks;

  public Long getMediaTypeId() {
    return mediaTypeId;
  }

  public void setMediaTypeId(Long mediaTypeId) {
    this.mediaTypeId = mediaTypeId;
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
