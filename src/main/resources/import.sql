insert into state(id, name) value (1, "Leinster");

insert into city(name, state_id) value ("Dublin", 1);

insert into cuisine(id, name) value (1, "Thailand");
insert into cuisine(id, name) value (2, "Indian");

insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated) value ("Thai Gourmet", 10, 1, "Pearse Street", "29", "D02 PP 50", "Grand canal dock", 1, utc_timestamp, utc_timestamp);
insert into restaurant(name, fee_delivery, cuisine_id, date_created, date_updated) value ("Thai Delivery", 9.5, 2, utc_timestamp, utc_timestamp);
insert into restaurant(name, fee_delivery, cuisine_id, date_created, date_updated) value ("Tuk Tuk Indian Food", 15, 2, utc_timestamp, utc_timestamp);

insert into payment_method(description) value ("Credit card");
insert into payment_method(description) value ("Debit card");
insert into payment_method(description) value ("Cash");

insert into permission(name, description) value ("READ_RESTAURANT", "User can read restaurants");

insert into restaurant_payment_method (restaurant_id, payment_method_id) value (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);


insert into product(name, price, description, active, restaurant_id) value ("Tom Yum Goong", 12, "Spicy Shrimp Soup",  true, 1);
insert into product(name, price, description, active, restaurant_id) value ("Tom Kha Kai", 10, "Spicy Green Papaya Salad", true, 2);
insert into product(name, price, description, active, restaurant_id) value ("Chicken Masala", 15, "It's a dish consisting of roasted marinated chicken chunks in spiced curry sauce", true, 3);