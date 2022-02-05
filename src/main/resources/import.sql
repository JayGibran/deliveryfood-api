insert into kitchen(id, name) values (1, "Indian");
insert into kitchen(id, name) values (2, "Italian");

insert into restaurant(name, fee_delivery, kitchen_id) values ("Thai Gourmet", 10, 1);
insert into restaurant(name, fee_delivery, kitchen_id) values ("Thai Delivery", 9.5, 2);
insert into restaurant(name, fee_delivery, kitchen_id) values ("Tuk Tuk Indian Food", 15, 2);

insert into payment_method(description) value ("Credit card");
insert into payment_method(description) value ("Debit card");
insert into payment_method(description) value ("Cash");

insert into permission(name, description) value("READ_RESTAURANT", "User can read restaurants");

insert into state(id, name) value (1, "PA");

insert into city(name, state_id) value ("Santarem", 1);