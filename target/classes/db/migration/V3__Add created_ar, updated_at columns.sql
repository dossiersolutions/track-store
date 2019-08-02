alter table album add column created_at timestamp not null default NOW();
alter table album add column updated_at timestamp not null default NOW();

alter table artist add column created_at timestamp not null default NOW();
alter table artist add column updated_at timestamp not null default NOW();

alter table genre add column created_at timestamp not null default NOW();
alter table genre add column updated_at timestamp not null default NOW();

alter table media_type add column created_at timestamp not null default NOW();
alter table media_type add column updated_at timestamp not null default NOW();

alter table playlist add column created_at timestamp not null default NOW();
alter table playlist add column updated_at timestamp not null default NOW();

alter table track add column created_at timestamp not null default NOW();
alter table track add column updated_at timestamp not null default NOW();
