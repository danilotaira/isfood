create table restaurant_user_responsible (
    restaurant_id bigint not null,
	user_access_id integer not null
);

alter table if exists restaurant_user_responsible add constraint fk_restaurants_users foreign key (user_access_id) references user_access;
alter table if exists restaurant_user_responsible add constraint fk_users_restaurants foreign key (restaurant_id) references restaurant;