create table city (id  serial not null, name varchar(255), state_id int4, primary key (id))
create table form_of_payment (id  serial not null, description varchar(255), primary key (id))
create table group_access (id  serial not null, name varchar(255) not null, primary key (id))
create table group_access_permissions (group_access_id int4 not null, permissions_id int4 not null)
create table kitchen (id  bigserial not null, name varchar(255) not null, primary key (id))
create table permission (id  serial not null, description varchar(255), name varchar(255), primary key (id))
create table product (id  serial not null, description varchar(255), name varchar(255), price numeric(19, 2), restaurant_id int8, primary key (id))
create table restaurant (id  bigserial not null, address_cep varchar(255), address_complement varchar(255), address_district varchar(255), address_number varchar(255), address_public_place varchar(255), date_created timestamp with time zone not null, date_modified timestamp with time zone not null, name varchar(255) not null, tax_shipping numeric(19, 2) not null, address_city_id int4, kitchen_id int8 not null, primary key (id))
create table restaurant_form_of_payments (restaurant_id int8 not null, form_of_payments_id int4 not null)
create table state (id  serial not null, name varchar(255), primary key (id))
create table user_access (id  serial not null, date_created timestamp with time zone not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id))
create table user_access_group_accesses (user_access_id int4 not null, group_accesses_id int4 not null)
alter table if exists city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state
alter table if exists group_access_permissions add constraint FKahqj56ynov9qfjtxvuktbuje5 foreign key (permissions_id) references permission
alter table if exists group_access_permissions add constraint FKeo9x0jyv6aua472hecgxhm6oa foreign key (group_access_id) references group_access
alter table if exists product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant
alter table if exists restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city
alter table if exists restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen
alter table if exists restaurant_form_of_payments add constraint FKavagrwsp6m7rgfhdfkyf7fsj6 foreign key (form_of_payments_id) references form_of_payment
alter table if exists restaurant_form_of_payments add constraint FKow0qeptcpgm8uk4o4bwh2gq4x foreign key (restaurant_id) references restaurant
alter table if exists user_access_group_accesses add constraint FKpbnjk4vx3wqo22w9uppnpr5ng foreign key (group_accesses_id) references group_access
alter table if exists user_access_group_accesses add constraint FK3www6u95v7l5prbv8hif7u4k5 foreign key (user_access_id) references user_access
create table city (id  serial not null, name varchar(255), state_id int4, primary key (id))
create table form_of_payment (id  serial not null, description varchar(255), primary key (id))
create table group_access (id  serial not null, name varchar(255) not null, primary key (id))
create table group_access_permissions (group_access_id int4 not null, permissions_id int4 not null)
create table kitchen (id  bigserial not null, name varchar(255) not null, primary key (id))
create table permission (id  serial not null, description varchar(255), name varchar(255), primary key (id))
create table product (id  serial not null, description varchar(255), name varchar(255), price numeric(19, 2), restaurant_id int8, primary key (id))
create table restaurant (id  bigserial not null, address_cep varchar(255), address_complement varchar(255), address_district varchar(255), address_number varchar(255), address_public_place varchar(255), date_created timestamp with time zone not null, date_modified timestamp with time zone not null, name varchar(255) not null, tax_shipping numeric(19, 2) not null, address_city_id int4, kitchen_id int8 not null, primary key (id))
create table restaurant_form_of_payments (restaurant_id int8 not null, form_of_payments_id int4 not null)
create table state (id  serial not null, name varchar(255), primary key (id))
create table user_access (id  serial not null, date_created timestamp with time zone not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id))
create table user_access_group_accesses (user_access_id int4 not null, group_accesses_id int4 not null)
alter table if exists city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state
alter table if exists group_access_permissions add constraint FKahqj56ynov9qfjtxvuktbuje5 foreign key (permissions_id) references permission
alter table if exists group_access_permissions add constraint FKeo9x0jyv6aua472hecgxhm6oa foreign key (group_access_id) references group_access
alter table if exists product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant
alter table if exists restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city
alter table if exists restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen
alter table if exists restaurant_form_of_payments add constraint FKavagrwsp6m7rgfhdfkyf7fsj6 foreign key (form_of_payments_id) references form_of_payment
alter table if exists restaurant_form_of_payments add constraint FKow0qeptcpgm8uk4o4bwh2gq4x foreign key (restaurant_id) references restaurant
alter table if exists user_access_group_accesses add constraint FKpbnjk4vx3wqo22w9uppnpr5ng foreign key (group_accesses_id) references group_access
alter table if exists user_access_group_accesses add constraint FK3www6u95v7l5prbv8hif7u4k5 foreign key (user_access_id) references user_access
