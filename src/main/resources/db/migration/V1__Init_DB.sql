--Hibernate: drop table if exists message cascade
--Hibernate: drop table if exists user_role cascade
--Hibernate: drop table if exists users cascade
--Hibernate: drop sequence if exists message_seq
--Hibernate: drop sequence if exists users_seq
--Hibernate: create sequence message_seq start with 1 increment by 50
--Hibernate: create sequence users_seq start with 1 increment by 50

--create sequence hibernate_sequence start 1 increment 1;

create sequence message_seq start with 1 increment by 50;
create sequence users_seq start with 1 increment by 50;

create table message (
  id bigint not null,
  filename varchar(255),
  tag varchar(255),
  text varchar(2048),
  user_id bigint,
  primary key (id)
  );

create table user_role (
  user_id bigint not null,
  roles varchar(255)
  );

create table users (
  id bigint not null,
  username varchar(255) unique not null,
  password varchar(255) not null,
  email varchar(255),
  active boolean not null,
  activation_code varchar(255),
  primary key (id)
  );

alter table if exists message
  add constraint message_user_fk
  foreign key (user_id) references users;

alter table if exists user_role
  add constraint user_role_user_fk
  foreign key (user_id) references users;