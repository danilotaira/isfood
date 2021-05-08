-- Table: public.city

-- DROP TABLE public.city;

-- Table: public.kitchen
CREATE TABLE kitchen (
	id bigserial primary key,
	name VARCHAR (60) not null
);

CREATE TABLE city (
	id serial primary key,
	name_city VARCHAR (80) not null,
	name_state VARCHAR (80) not null
);

CREATE TABLE public.city
(
    id integer NOT NULL DEFAULT nextval('city_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    state_id integer,
    CONSTRAINT city_pkey PRIMARY KEY (id),
    CONSTRAINT fk6p2u50v8fg2y0js6djc6xanit FOREIGN KEY (state_id)
        REFERENCES public.state (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.city
    OWNER to admin;

-- Table: public.form_of_payment

-- DROP TABLE public.form_of_payment;

CREATE TABLE public.form_of_payment
(
    id integer NOT NULL DEFAULT nextval('form_of_payment_id_seq'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT form_of_payment_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.form_of_payment
    OWNER to admin;

-- Table: public.group_access

-- DROP TABLE public.group_access;

CREATE TABLE public.group_access
(
    id integer NOT NULL DEFAULT nextval('group_access_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT group_access_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.group_access
    OWNER to admin;

-- Table: public.group_access_permissions

-- DROP TABLE public.group_access_permissions;

CREATE TABLE public.group_access_permissions
(
    group_access_id integer NOT NULL,
    permissions_id integer NOT NULL,
    CONSTRAINT fkahqj56ynov9qfjtxvuktbuje5 FOREIGN KEY (permissions_id)
        REFERENCES public.permission (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkeo9x0jyv6aua472hecgxhm6oa FOREIGN KEY (group_access_id)
        REFERENCES public.group_access (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.group_access_permissions
    OWNER to admin;




    OWNER to admin;

-- Table: public.kitchen_restaurants

-- DROP TABLE public.kitchen_restaurants;

CREATE TABLE public.kitchen_restaurants
(
    kitchen_id bigint NOT NULL,
    restaurants_id bigint NOT NULL,
    CONSTRAINT uk_61c087dimkcedh4jvmohrwkox UNIQUE (restaurants_id)
)

TABLESPACE pg_default;

ALTER TABLE public.kitchen_restaurants
    OWNER to admin;

-- Table: public.permission

-- DROP TABLE public.permission;

CREATE TABLE public.permission
(
    id integer NOT NULL DEFAULT nextval('permission_id_seq'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT permission_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.permission
    OWNER to admin;


-- Table: public.product

-- DROP TABLE public.product;

CREATE TABLE public.product
(
    id integer NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    price numeric(19,2),
    restaurant_id bigint,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT fkp4b7e36gck7975p51raud8juk FOREIGN KEY (restaurant_id)
        REFERENCES public.restaurant (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.product
    OWNER to admin;

-- Table: public.restaurant

-- DROP TABLE public.restaurant;

CREATE TABLE public.restaurant
(
    id bigint NOT NULL DEFAULT nextval('restaurant_id_seq'::regclass),
    address_cep character varying(255) COLLATE pg_catalog."default",
    address_complement character varying(255) COLLATE pg_catalog."default",
    address_district character varying(255) COLLATE pg_catalog."default",
    address_number character varying(255) COLLATE pg_catalog."default",
    address_public_place character varying(255) COLLATE pg_catalog."default",
    date_created timestamp with time zone NOT NULL,
    date_modified timestamp with time zone NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    tax_shipping numeric(19,2) NOT NULL,
    address_city_id integer,
    kitchen_id bigint NOT NULL,
    CONSTRAINT restaurant_pkey PRIMARY KEY (id),
    CONSTRAINT fk8pcwgn41lfg43d8u82ewn3cn FOREIGN KEY (address_city_id)
        REFERENCES public.city (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkrur1dqx79jim8s8dvdp16qc3d FOREIGN KEY (kitchen_id)
        REFERENCES public.kitchen (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.restaurant
    OWNER to admin;

-- Table: public.restaurant_form_of_payments

-- DROP TABLE public.restaurant_form_of_payments;

CREATE TABLE public.restaurant_form_of_payments
(
    restaurant_id bigint NOT NULL,
    form_of_payments_id integer NOT NULL,
    CONSTRAINT fkavagrwsp6m7rgfhdfkyf7fsj6 FOREIGN KEY (form_of_payments_id)
        REFERENCES public.form_of_payment (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkow0qeptcpgm8uk4o4bwh2gq4x FOREIGN KEY (restaurant_id)
        REFERENCES public.restaurant (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.restaurant_form_of_payments
    OWNER to admin;

-- Table: public.restaurant_products

-- DROP TABLE public.restaurant_products;

CREATE TABLE public.restaurant_products
(
    restaurant_id bigint NOT NULL,
    products_id integer NOT NULL,
    CONSTRAINT uk_7w9g2khikordc6r0nesabrhtn UNIQUE (products_id)
)

TABLESPACE pg_default;

ALTER TABLE public.restaurant_products
    OWNER to admin;

-- Table: public.state

-- DROP TABLE public.state;

CREATE TABLE public.state
(
    id integer NOT NULL DEFAULT nextval('state_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT state_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.state
    OWNER to admin;

-- Table: public.user_access

-- DROP TABLE public.user_access;

CREATE TABLE public.user_access
(
    id integer NOT NULL DEFAULT nextval('user_access_id_seq'::regclass),
    date_created timestamp with time zone NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_access_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.user_access
    OWNER to admin;

-- Table: public.user_access_group_accesses

-- DROP TABLE public.user_access_group_accesses;

CREATE TABLE public.user_access_group_accesses
(
    user_access_id integer NOT NULL,
    group_accesses_id integer NOT NULL,
    CONSTRAINT fk3www6u95v7l5prbv8hif7u4k5 FOREIGN KEY (user_access_id)
        REFERENCES public.user_access (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkpbnjk4vx3wqo22w9uppnpr5ng FOREIGN KEY (group_accesses_id)
        REFERENCES public.group_access (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.user_access_group_accesses
    OWNER to admin;


-- ## CREATE SEQUENCE TABLES ##########################################


-- SEQUENCE: public.city_id_seq

-- DROP SEQUENCE public.city_id_seq;

CREATE SEQUENCE public.city_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.city_id_seq
    OWNER TO admin;


-- SEQUENCE: public.form_of_payment_id_seq

-- DROP SEQUENCE public.form_of_payment_id_seq;

CREATE SEQUENCE public.form_of_payment_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.form_of_payment_id_seq
    OWNER TO admin;

-- SEQUENCE: public.group_access_id_seq

-- DROP SEQUENCE public.group_access_id_seq;

CREATE SEQUENCE public.group_access_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.group_access_id_seq
    OWNER TO admin;

-- SEQUENCE: public.kitchen_id_seq

-- DROP SEQUENCE public.kitchen_id_seq;

CREATE SEQUENCE public.kitchen_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.kitchen_id_seq
    OWNER TO admin;

-- SEQUENCE: public.permission_id_seq

-- DROP SEQUENCE public.permission_id_seq;

CREATE SEQUENCE public.permission_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.permission_id_seq
    OWNER TO admin;

-- SEQUENCE: public.product_id_seq

-- DROP SEQUENCE public.product_id_seq;

CREATE SEQUENCE public.product_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.product_id_seq
    OWNER TO admin;

-- SEQUENCE: public.restaurant_id_seq

-- DROP SEQUENCE public.restaurant_id_seq;

CREATE SEQUENCE public.restaurant_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.restaurant_id_seq
    OWNER TO admin;

-- SEQUENCE: public.state_id_seq

-- DROP SEQUENCE public.state_id_seq;

CREATE SEQUENCE public.state_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.state_id_seq
    OWNER TO admin;

-- SEQUENCE: public.user_access_id_seq

-- DROP SEQUENCE public.user_access_id_seq;

CREATE SEQUENCE public.user_access_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.user_access_id_seq
    OWNER TO admin;


