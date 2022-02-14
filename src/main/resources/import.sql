insert into cuisine(id, name) value (1, "Thailand");
insert into cuisine(id, name) value (2, "Indian");

insert into restaurant(name, fee_delivery, cuisine_id) value ("Thai Gourmet", 10, 1);
insert into restaurant(name, fee_delivery, cuisine_id) value ("Thai Delivery", 9.5, 2);
insert into restaurant(name, fee_delivery, cuisine_id) value ("Tuk Tuk Indian Food", 15, 2);

insert into payment_method(description) value ("Credit card");
insert into payment_method(description) value ("Debit card");
insert into payment_method(description) value ("Cash");

insert into permission(name, description) value ("READ_RESTAURANT", "User can read restaurants");

insert into state(id, name) value (1, "PA");

insert into city(name, state_id) value ("Santarem", 1);

insert into restaurant_payment_method (restaurant_id, payment_method_id) value (1, 1), (1,2), (1,3), (2, 3), (3, 2), (3, 3);