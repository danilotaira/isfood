create table permission (
	id  serial not null,
	description varchar(150),
	name varchar(60),
	primary key (id)
);

create table group_access_permissions (
	group_access_id integer not null,
	permissions_id integer not null
);

alter table if exists group_access_permissions add constraint fk_group_permissions foreign key (permissions_id) references permission;
alter table if exists group_access_permissions add constraint fk_permissions_group foreign key (group_access_id) references group_access;

