create table form_of_payment (
	id serial not null,
	description varchar(50),
	primary key (id)
);

create table restaurant_form_of_payments (
	restaurant_id integer not null,
	form_of_payments_id integer not null
);

alter table if exists restaurant_form_of_payments add constraint fk_restaurant_form_of_payment foreign key (form_of_payments_id) references form_of_payment ;
alter table if exists restaurant_form_of_payments add constraint fk_form_of_payment_restaurant foreign key (restaurant_id) references restaurant ;