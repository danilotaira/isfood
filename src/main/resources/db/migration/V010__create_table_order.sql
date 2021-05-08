create table order_customer (
	id bigserial not null,
    status_order varchar(10) not null,

    address_cep varchar(9),
	address_complement varchar(50),
	address_district varchar(60),
	address_number varchar(5),
	address_public_place varchar(150),

	subtotal numeric(19, 2) not null,
	tax_shipping numeric(19, 2) not null,
	grand_total numeric(19, 2) not null,
	date_created timestamp with time zone not null,
	date_confirmation timestamp with time zone,
    date_cancellation timestamp with time zone,
    date_delivery timestamp with time zone,

    restaurant_id bigint not null,
    form_of_payment_id integer not null,
    user_access_id integer not null,
    address_city_id integer not null,

	primary key (id)
);

alter table if exists order_customer add constraint fk_order_customer_address_city foreign key (address_city_id) references city;
alter table if exists order_customer add constraint fk_order_customer_form_of_payment foreign key (form_of_payment_id) references form_of_payment;
alter table if exists order_customer add constraint fk_order_customer_user_access foreign key (user_access_id) references user_access;
alter table if exists order_customer add constraint fk_order_customer_restaurant foreign key (restaurant_id) references restaurant;

create table item_order (
	id bigserial not null,
    quantity numeric(19, 2) not null,
    price_unit numeric(19, 2) not null,
    price_total numeric(19, 2) not null,
    note text null,

    order_customer_id bigint not null,
    product_id bigint not null,

	primary key (id),
	unique (order_customer_id, product_id)
);

alter table if exists item_order add constraint fk_item_order_order_customer foreign key (order_customer_id) references order_customer;
alter table if exists item_order add constraint fk_item_order_product_id foreign key (product_id) references product;