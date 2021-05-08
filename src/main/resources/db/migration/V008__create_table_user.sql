create table user_access (
	id  serial not null,
	date_created timestamp with time zone not null,
	email varchar(150) not null,
	name varchar(80) not null,
	password varchar(20) not null,
	primary key (id)
);

create table user_access_group_accesses (
	user_access_id integer not null,
	group_accesses_id integer not null
);

alter table if exists user_access_group_accesses add constraint fk_users_group foreign key (group_accesses_id) references group_access;
alter table if exists user_access_group_accesses add constraint fk_group_users foreign key (user_access_id) references user_access;