CREATE SEQUENCE store_album_id_seq
  INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 348;

ALTER SEQUENCE store_album_id_seq OWNED BY album.id;
------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE store_artist_id_seq
  INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 276;

ALTER SEQUENCE store_artist_id_seq OWNED BY artist.id;
------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE store_genre_id_seq
  INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 26;

ALTER SEQUENCE store_genre_id_seq OWNED BY genre.id;
------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE store_media_type_id_seq
  INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 6;

ALTER SEQUENCE store_media_type_id_seq OWNED BY media_type.id;
------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE store_playlist_id_seq
  INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 19;

ALTER SEQUENCE store_playlist_id_seq OWNED BY playlist.id;
------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE store_track_id_seq
  INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 3504;

ALTER SEQUENCE store_track_id_seq OWNED BY track.id;
------------------------------------------------------------------------------------------------------------------------