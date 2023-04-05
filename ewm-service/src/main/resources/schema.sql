drop table if exists users, categories, events, locations, requests, compilations, events_compilations, comments;

create table if not exists users
(
    user_id integer generated always as identity
        constraint "pk_users"
            primary key,
    email   varchar(255) not null,
    name    varchar(32)  not null,
    constraint uq_user_email unique (email)
);

create table if not exists categories
(
    category_id integer generated always as identity
        constraint "pk_categories"
            primary key,
    name        varchar(32) not null,
    constraint uq_category_name unique (name)
);

create table if not exists locations
(
    location_id integer generated always as identity
        constraint "pk_locations"
            primary key,
    lat         real not null,
    lon         real not null
);

create table if not exists compilations
(
    compilation_id integer generated always as identity
        constraint "pk_compilations"
            primary key,
    pinned         boolean default false,
    title          varchar(120) not null
);

create table if not exists events
(
    event_id           integer generated always as identity
        constraint "pk_events"
            primary key,
    created            timestamp without time zone not null,
    published          timestamp without time zone,
    annotation         varchar(2000)               not null,
    category_id        integer
        constraint events_categories_null_fk
            references categories (category_id),
    description        varchar(7000)               not null,
    event_date         timestamp without time zone not null,
    initiator_id       integer
        constraint events_users_null_fk
            references users (user_id),
    location_id        integer
        constraint events_locations_null_fk
            references locations (location_id),
    paid               boolean default false,
    participant_limit  integer default 0,
    request_moderation boolean default true,
    state              varchar(16)                 not null,
    title              varchar(120)                not null
);

create table if not exists events_compilations
(
    event_id       integer
        constraint events_null_fk
            references events (event_id),
    compilation_id integer
        constraint compilations_null_fk
            references compilations (compilation_id)
);

create table if not exists requests
(
    request_id   integer generated always as identity
        constraint "pk_requests"
            primary key,
    created      timestamp without time zone not null,
    event_id     integer
        constraint requests_events_null_fk
            references events (event_id),
    requester_id integer
        constraint requests_users_null_fk
            references users (user_id),
    state        varchar(16)                 not null
);

create table if not exists comments
(
    comment_id integer generated always as identity
        constraint "pk_comments"
            primary key,
    created    timestamp without time zone not null,
    updated    timestamp without time zone not null,
    event_id   integer
        constraint comments_events_null_fk
            references events (event_id),
    author_id  integer
        constraint comments_users_null_fk
            references users (user_id),
    state      varchar(16)                 not null,
    text       varchar(7000)               not null
)