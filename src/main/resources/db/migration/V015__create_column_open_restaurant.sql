alter table order_customer add uuid VARCHAR (36) not null;
alter table order_customer add constraint uk_order_uuid unique (uuid);