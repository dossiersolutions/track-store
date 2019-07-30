CREATE TABLE "album"
(
    "id" INT NOT NULL,
    "title" VARCHAR(160) NOT NULL,
    "artist_id" INT NOT NULL,
    CONSTRAINT "pk_album" PRIMARY KEY  ("id")
);

CREATE TABLE "artist"
(
    "id" INT NOT NULL,
    "name" VARCHAR(120),
    CONSTRAINT "pk_artist" PRIMARY KEY  ("id")
);

CREATE TABLE "genre"
(
    "id" INT NOT NULL,
    "name" VARCHAR(120),
    CONSTRAINT "pk_genre" PRIMARY KEY  ("id")
);

CREATE TABLE "media_type"
(
    "id" INT NOT NULL,
    "name" VARCHAR(120),
    CONSTRAINT "pk_media_type" PRIMARY KEY  ("id")
);

CREATE TABLE "playlist"
(
    "id" INT NOT NULL,
    "name" VARCHAR(120),
    CONSTRAINT "pk_playlist" PRIMARY KEY  ("id")
);

CREATE TABLE "playlist_track"
(
    "playlist_id" INT NOT NULL,
    "track_id" INT NOT NULL,
    CONSTRAINT "pk_playlist_track" PRIMARY KEY  ("playlist_id", "track_id")
);

CREATE TABLE "track"
(
    "id" INT NOT NULL,
    "name" VARCHAR(200) NOT NULL,
    "album_id" INT,
    "media_type_id" INT NOT NULL,
    "genre_id" INT,
    "composer" VARCHAR(220),
    "milliseconds" INT NOT NULL,
    "bytes" INT,
    "unit_price" NUMERIC(10,2) NOT NULL,
    CONSTRAINT "pk_track" PRIMARY KEY  ("id")
);

/*******************************************************************************
   Create Foreign Keys
********************************************************************************/
ALTER TABLE "album" ADD CONSTRAINT "fk__album_artist_id"
    FOREIGN KEY ("artist_id") REFERENCES "artist" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE INDEX "ifk__album_artist_id" ON "album" ("artist_id");

ALTER TABLE "playlist_track" ADD CONSTRAINT "fk__playlist_track_playlist_id"
    FOREIGN KEY ("playlist_id") REFERENCES "playlist" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "playlist_track" ADD CONSTRAINT "fk__playlist_track_track_id"
    FOREIGN KEY ("track_id") REFERENCES "track" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE INDEX "ifk__playlist_track_track_id" ON "playlist_track" ("track_id");

ALTER TABLE "track" ADD CONSTRAINT "fk__track_album_id"
    FOREIGN KEY ("album_id") REFERENCES "album" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE INDEX "ifk__track_album_id" ON "track" ("album_id");

ALTER TABLE "track" ADD CONSTRAINT "fk__track_genre_id"
    FOREIGN KEY ("genre_id") REFERENCES "genre" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE INDEX "ifk__track_genre_id" ON "track" ("genre_id");

ALTER TABLE "track" ADD CONSTRAINT "fk__track_media_type_id"
    FOREIGN KEY ("media_type_id") REFERENCES "media_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE INDEX "ifk__track_media_type_id" ON "track" ("media_type_id");