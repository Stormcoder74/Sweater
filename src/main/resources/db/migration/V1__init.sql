create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE message (
    id  bigserial NOT NULL,
    text VARCHAR(2048) NOT NULL,
    tag VARCHAR(32),
    user_id int8,
    filename VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE user_role (
    user_id int8 not null,
    roles varchar(16)
);

create table users (
    id int8 not null,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    active boolean not null,
    email varchar(255),
    activation_code varchar(255),
    primary key (id)
);

alter table if exists message
    add constraint message_user_fk
    foreign key (user_id) references users;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references users;