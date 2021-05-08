CREATE TABLE state (
	id serial primary key,
	name VARCHAR (80) not null
);

insert into state (name)
select DISTINCT name_state from city order by name_state ASC;

alter table city add column state_id integer not null default 0;

update city as c set state_id = (select s.id from state s where s.name = c.name_state);

ALTER TABLE city
ADD CONSTRAINT fk_city_state FOREIGN KEY (state_id) REFERENCES state;

alter table city drop column name_state;
alter table city RENAME name_city to name;
alter table city
	alter column name type varchar (80),
	alter column name set NOT NULL;