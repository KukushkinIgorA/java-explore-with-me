drop table if exists hits;

create table if not exists hits
(
    hit_id  integer generated always as identity
        constraint "pk_hits"
            primary key,
    app     varchar(32) not null,
    uri    varchar(255) not null,
    ip    varchar(16) not null,
    hit_timestamp    timestamp without time zone not null
);