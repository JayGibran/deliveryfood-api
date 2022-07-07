alter table order_
    add code varchar(36) not null after id;
update order_
set code = uuid();
alter table order_
    add constraint uk_order_code unique (code);