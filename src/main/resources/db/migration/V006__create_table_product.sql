create table product (
	id serial not null,
	description varchar(255),
	name varchar(60) not null,
	price numeric(19, 2) not null,
	restaurant_id integer not null,
	primary key (id)
);

alter table if exists product add constraint fk_product_restaurant foreign key (restaurant_id) references restaurant ;