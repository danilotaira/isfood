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
ALTER SEQUENCE permission_id_seq RESTART WITH 3;

insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified, address_cep, address_complement, address_district, address_number, address_public_place, address_city_id) values ('New Flavor', 5.2, 1, current_timestamp, current_timestamp,'03518-040', 'casa', 'jd fernandes', 193, 'rua canhamo do canada', 3);
insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified) values ('Taj Mahal Palace', 4.3, 2, current_timestamp, current_timestamp);
insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified) values ('Brazilian Party', 9.9, 3, current_timestamp, current_timestamp);
insert into restaurant (name, tax_shipping, kitchen_id, date_created, date_modified) values ('Sujinho', 12.1, 3, current_timestamp, current_timestamp);

insert into product (name, description, price, restaurant_id) values ('Italian Bread', 'Italian Bread perfect, very good', 3.9, 3);
insert into product (name, description, price, restaurant_id) values ('French fries', 'French fries, very good', 6.1, 1);
insert into product (name, description, price, restaurant_id) values ('French fries', 'French fries, very good', 6.1, 2);
insert into product (name, description, price, restaurant_id) values ('French fries', 'French fries, very good', 6.1, 4);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 1);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 2);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 3);
insert into product (name, description, price, restaurant_id) values ('Rice', 'White Rice, its good', 4, 4);

insert into restaurant_form_of_payments (restaurant_id, form_of_payments_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3), (4,1) ;