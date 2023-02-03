create table if not exists player_points
(
    uuid   VARCHAR(255)     not null,
    points bigint default 0 not null,
    primary key (uuid)
);