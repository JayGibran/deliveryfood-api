set
foreign_key_checks = 0;

delete
from city;
delete
from cuisine;
delete
from group_;
delete
from group_permission;
delete
from payment_method;
delete
from permission;
delete
from product;
delete
from restaurant;
delete
from restaurant_payment_method;
delete
from state;
delete
from user;
delete
from user_group;

set
foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table cuisine auto_increment = 1;
alter table group_ auto_increment = 1;
alter table group_permission auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table restaurant_payment_method auto_increment = 1;
alter table state auto_increment = 1;
alter table user auto_increment = 1;
alter table user_group auto_increment = 1;

insert into state(id, name) value (1, "Leinster");
insert into state(id, name) value (2, "Munster");

insert into city(name, state_id) value ("Dublin", 1);
insert into city(name, state_id) value ("Cork", 2);

insert into cuisine(id, name) value (1, "Thailand");
insert into cuisine(id, name) value (2, "Indian");
insert into cuisine(id, name) value (3, "Brazilian");
insert into cuisine(id, name) value (4, "Italian");

insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated, active) value ("Thai Gourmet", 10, 1, "Pearse Street", "29", "D02 PP 50", "Grand canal dock", 1, utc_timestamp, utc_timestamp, true);
insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated, active) value ("Thai Delivery", 9.5, 2, "Dame Street", "50", "D02 PP 150", "City Center", 1, utc_timestamp, utc_timestamp, true);
insert into restaurant(name, fee_delivery, cuisine_id, date_created, date_updated, active) value ("Tuk Tuk Indian Food", 15, 2, utc_timestamp, utc_timestamp, true);
insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated, active) value ("BAH33º THE AUTHENTIC GAUCHO BBQ", 11, 3, "Dawson St", "Unit 3-5", "D02 X272", "Royal Hibernian Way", 1, utc_timestamp, utc_timestamp, true);


insert into payment_method(description) value ("Credit card");
insert into payment_method(description) value ("Debit card");
insert into payment_method(description) value ("Cash");

insert into permission(id, name, description) value (1, "READ_RESTAURANT", "User can read restaurants");

insert into group_(id, name) value (1, "Driver");

insert into group_permission(group_id, permission_id) value (1, 1);

insert into restaurant_payment_method (restaurant_id, payment_method_id) value (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into product(name, price, description, active, restaurant_id) value ("Tom Yum Goong", 12, "Spicy Shrimp Soup",  true, 1);
insert into product(name, price, description, active, restaurant_id) value ("Tom Kha Kai", 10, "Spicy Green Papaya Salad", true, 2);
insert into product(name, price, description, active, restaurant_id) value ("Chicken Masala", 15, "It's a dish consisting of roasted marinated chicken chunks in spiced curry sauce", true, 3);

