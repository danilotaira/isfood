create table restaurant (
	id  bigserial not null,
	address_cep varchar(9),
	address_complement varchar(50),
	address_district varchar(60),
	address_number varchar(5),
	address_public_place varchar(150),
	date_created timestamp with time zone not null,
	date_modified timestamp with time zone not null,
	name varchar(150) not null,
	tax_shipping numeric(19, 2) not null,
	address_city_id integer,
	kitchen_id integer not null,
	primary key (id)
);

alter table if exists restaurant add constraint fk_restaurant_city foreign key (address_city_id) references city;
alter table if exists restaurant add constraint fk_restaurant_kitchen foreign key (kitchen_id) references kitchen;