package com.trackstore.store;

public class StoreApplicationView {

  public interface SimpleView {}

  public interface ArtistDetailedView extends SimpleView {}

  public interface AlbumDetailedView extends SimpleView {}

  public interface TrackDetailedView extends SimpleView {}

}
