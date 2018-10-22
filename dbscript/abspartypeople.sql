drop database abspartypeople;

create database abspartypeople;

use abspartypeople;

create table role(
  id                int(6) auto_increment not null primary key,
  name              varchar(20) not null
);

insert into role(name)
  values('Admin');
insert into role(name)
  values('User');

create table user(
  id                int(6) auto_increment not null primary key,
  first_name        varchar(50) not null,
  last_name         varchar(50) not null,
  date_of_birth     date not null,
  credential        varchar(20) not null,
  password          varchar(100) not null,
  photo             longblob,
  role_id           int(6) not null
);

alter table user
  add constraint fk_user_role_id foreign key(role_id) references role(id) on delete cascade;

create table post(
  id                int(6) auto_increment not null primary key,
  content           varchar(1000) not null,
  user_id           int(6) not null,
  created_at        timestamp not null,
  location          varchar(50)
);

alter table post
  add constraint fk_post_user_id foreign key(user_id) references user(id) on delete cascade;

create table attachment (
  id                int(6) auto_increment not null primary key,
  content           longblob not null,
  post_id           int(6) not null
);

alter table attachment
  add constraint fk_attachment_post_id foreign key(post_id) references post(id) on delete cascade;
  
create table rating (
  id                int(6) auto_increment not null primary key,
  post_id           int(6) not null,
  user_id           int(6) not null,
  mark              int(2) not null
);

alter table rating
  add constraint fk_rating_post_id foreign key(post_id) references post(id) on delete cascade;
alter table rating
  add constraint fk_rating_user_id foreign key(user_id) references user(id) on delete cascade;