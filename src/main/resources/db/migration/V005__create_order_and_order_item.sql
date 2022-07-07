create table order_
(
    id                   bigint         not null auto_increment,
    user_id              bigint         not null,
    restaurant_id        bigint         not null,
    payment_method_id    bigint         not null,
    sub_total            decimal(10, 2) not null,
    total                decimal(10, 2) not null,
    fee_delivery         decimal(10, 2) not null,
    date_created         datetime       not null,
    date_confirmation    datetime,
    date_cancelation     datetime,
    date_delivered       datetime,
    status               varchar(10)    not null,
    address_city_id      bigint         not null,
    address_additional   varchar(60),
    address_air_code     varchar(10),
    address_neighborhood varchar(60)    not null,
    address_number       varchar(20)    not null,
    address_street       varchar(100)   not null,
    primary key (id),
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_order_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id),
    CONSTRAINT fk_order_address_city FOREIGN KEY (address_city_id) REFERENCES city (id),
    CONSTRAINT fk_order_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
) engine = InnoDB
  default charset = utf8;


create table order_item
(
    id         bigint         not null auto_increment,
    quantity   smallint(6)    not null,
    unit_price decimal(10, 2) not null,
    total      decimal(10, 2) not null,
    note       varchar(255)   null,
    order_id   bigint         not null,
    product_id bigint         not null,
    primary key (id),
    unique key uk_order_item_product (order_id, product_id),
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES order_ (id),
    constraint fk_order_item_product foreign key (product_id) references product (id)
) engine = InnoDB
  default charset = utf8;