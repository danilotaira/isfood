SET session_replication_role = 'replica';
delete from kitchen;
delete from city;
delete from state;
delete from form_of_payment;
delete from permission;
delete from restaurant;
delete from product;
delete from restaurant_form_of_payments;
delete from group_access;
delete from group_access_permissions;
delete from user_access;
delete from user_access_group_accesses;
delete from order_customer;
delete from item_order  ;


insert into kitchen (id, name) values (1, 'Tailandesa');
insert into kitchen (id, name) values (2, 'Indiana');
insert into kitchen (id, name) values (3, 'Brasileira');
ALTER SEQUENCE kitchen_id_seq RESTART WITH 4;

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');
ALTER SEQUENCE state_id_seq RESTART WITH 4;

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);
ALTER SEQUENCE city_id_seq RESTART WITH 6;

insert into form_of_payment (id, description) values (1, 'Credit card');
insert into form_of_payment (id, description) values (2, 'Debit card');
insert into form_of_payment (id, description) values (3, 'Cash');
ALTER SEQUENCE form_of_payment_id_seq RESTART WITH 4;

insert into permission (id, name, description) values (1, 'CONSULT_KITCHENS', 'Allows you to consult kitchens');
insert into permission (id, name, description) values (2, 'EDIT_KITCHENS', 'Allows you to edit kitchens');
insert into permission (id, name, description) values (3, 'DELETE_KITCHENS', 'Allows you to delete kitchens');
ALTER SEQUENCE permission_id_seq RESTART WITH 4;

ALTER SEQUENCE restaurant_id_seq RESTART WITH 1;
insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified, address_cep, address_complement, address_district, address_number, address_public_place, address_city_id, active) values ('New Flavor', 5.2, 1, current_timestamp, current_timestamp,'03518-040', 'casa', 'jd fernandes', 193, 'rua canhamo do canada', 3, true);
insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified, active) values ('Taj Mahal Palace', 4.3, 2, current_timestamp, current_timestamp,true);
insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified, active) values ('Brazilian Party', 9.9, 3, current_timestamp, current_timestamp,true);
insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified, active) values ('Sujinho', 12.1, 3, current_timestamp, current_timestamp,true);

ALTER SEQUENCE product_id_seq RESTART WITH 1;
insert into product (name, description, price, restaurant_id) values ('Italian Bread', 'Italian Bread perfect, very good', 3.9, 3);
insert into product (name, description, price, restaurant_id) values ('French fries', 'French fries, very good', 6.1, 1);
insert into product (name, description, price, restaurant_id) values ('French fries', 'French fries, very good', 6.1, 2);
insert into product (name, description, price, restaurant_id) values ('French fries', 'French fries, very good', 6.1, 4);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 1);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 2);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 3);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 4);

insert into restaurant_form_of_payments (restaurant_id, form_of_payments_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3), (4,1) ;

ALTER SEQUENCE group_access_id_seq RESTART WITH 1;
insert into group_access (name) values ('USERS');
insert into group_access (name) values ('ADMINISTRATORS');
insert into group_access (name) values ('SUPERVISORS');

insert into group_access_permissions values (1, 1);
insert into group_access_permissions values (2, 1);
insert into group_access_permissions values (2, 2);
insert into group_access_permissions values (3, 1);
insert into group_access_permissions values (3, 2);
insert into group_access_permissions values (3, 3);

ALTER SEQUENCE user_access_id_seq RESTART WITH 1;
insert into user_access (date_created, email, name, password) values (current_timestamp, 'user@user.com', 'user', '123');
insert into user_access (date_created, email, name, password) values (current_timestamp, 'supervisor@supervisor.com', 'supervisor', '123');
insert into user_access (date_created, email, name, password) values (current_timestamp, 'admin@admin.com', 'admin', '123');

insert into user_access_group_accesses values (1, 1);
insert into user_access_group_accesses values (2, 1);
insert into user_access_group_accesses values (2, 2);
insert into user_access_group_accesses values (3, 1);
insert into user_access_group_accesses values (3, 2);
insert into user_access_group_accesses values (3, 3);

ALTER SEQUENCE order_customer_id_seq RESTART WITH 1;
insert into order_customer(status_order,
address_city_id, address_cep, address_complement, address_district, address_number, address_public_place,
subtotal, tax_shipping, grand_total,
date_created, date_confirmation, date_cancellation, date_delivery,
restaurant_id, form_of_payment_id, user_access_id) values (
'CRIADO',
2, '03580-010', 'casa', 'jd fernandes', 193, 'rua canhamo do canadá',
125.00, 5.00, 130.00,
current_timestamp, null, null, null,
2, 2, 1
);
insert into order_customer(status_order,
address_city_id, address_cep, address_complement, address_district, address_number, address_public_place,
subtotal, tax_shipping, grand_total,
date_created, date_confirmation, date_cancellation, date_delivery,
restaurant_id, form_of_payment_id, user_access_id) values (
'ENTREGUE',
2, '03518-040', '1 andar', 'vila euthalia', 2, 'rua coronel peroba',
230.00, 10.00, 240.00,
current_timestamp, current_timestamp, null, current_timestamp,
1, 1, 2
);

ALTER SEQUENCE item_order_id_seq RESTART WITH 1;
insert into item_order (quantity,price_unit,price_total,note,
order_customer_id, product_id) values (
3, 25.50, 76.50, null,
1, 1);
insert into item_order (quantity,price_unit,price_total,note,
order_customer_id, product_id) values (
1, 49.00, 49.00, null,
1, 2);

insert into item_order (quantity,price_unit,price_total,note,
order_customer_id, product_id) values (
2, 55.50, 111.00, null,
2, 3);
insert into item_order (quantity,price_unit,price_total,note,
order_customer_id, product_id) values (
2, 35.00, 70.00, null,
2, 4);
insert into item_order (quantity,price_unit,price_total,note,
order_customer_id, product_id) values (
1, 40.00, 40.00, null,
2, 5);



SET session_replication_role = 'origin';