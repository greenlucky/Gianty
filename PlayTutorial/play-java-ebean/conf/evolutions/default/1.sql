# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table role (
  id                            bigint auto_increment not null,
  name                          varchar(255) not null,
  role_type_id                  bigint,
  constraint pk_role primary key (id)
);

create table role_type (
  id                            bigint auto_increment not null,
  name                          varchar(255) not null,
  constraint pk_role_type primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(50) not null,
  create_date                   bigint not null,
  constraint pk_user primary key (id)
);

create table user_role (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  role_id                       bigint,
  created_date                  bigint not null,
  modified_date                 bigint not null,
  constraint pk_user_role primary key (id)
);

alter table role add constraint fk_role_role_type_id foreign key (role_type_id) references role_type (id) on delete restrict on update restrict;
create index ix_role_role_type_id on role (role_type_id);

alter table user_role add constraint fk_user_role_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_role_user_id on user_role (user_id);

alter table user_role add constraint fk_user_role_role_id foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_user_role_role_id on user_role (role_id);


# --- !Downs

alter table role drop constraint if exists fk_role_role_type_id;
drop index if exists ix_role_role_type_id;

alter table user_role drop constraint if exists fk_user_role_user_id;
drop index if exists ix_user_role_user_id;

alter table user_role drop constraint if exists fk_user_role_role_id;
drop index if exists ix_user_role_role_id;

drop table if exists role;

drop table if exists role_type;

drop table if exists user;

drop table if exists user_role;

