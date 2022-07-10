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
from restaurant_user_responsible;
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
delete
from order_item;
delete
from order_;

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
alter table restaurant_user_responsible auto_increment = 1;
alter table order_item auto_increment = 1;
alter table order_ auto_increment = 1;


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
insert into permission(id, name, description) value (2, "EDIT_RESTAURANT", "User can edit restaurants");
insert into permission(id, name, description) value (3, "ADD_PRODUCT", "User can add products");

insert into group_(id, name) value (1, "Cook");
insert into group_(id, name) value (2, "Driver");

insert into group_permission(group_id, permission_id) value (1, 1);
insert into group_permission(group_id, permission_id) value (1, 2);
insert into group_permission(group_id, permission_id) value (2, 1);
insert into group_permission(group_id, permission_id) value (2, 2);

insert into restaurant_payment_method (restaurant_id, payment_method_id) value (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (4, 3);

insert into product(name, price, description, active, restaurant_id) value ("Tom Yum Goong", 12, "Spicy Shrimp Soup",  true, 1);
insert into product(name, price, description, active, restaurant_id) value ("Tom Kha Kai", 10, "Spicy Green Papaya Salad", true, 2);
insert into product(name, price, description, active, restaurant_id) value ("Chicken Masala", 15, "It's a dish consisting of roasted marinated chicken chunks in spiced curry sauce", true, 3);
insert into product(name, price, description, active, restaurant_id) value ("Picanha Dry Aged", 29.90, "10OZ of traditional Brazilian Rump Cap accompanied with rustic potatoes and Caprese salad.", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Costela Gaúcha 12 horas", 29.90, "Juicy 12-Hour Slow-Cooked Beef Ribs accompanied by rustic potatoes and mixed salad.", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Bife de Chorizo", 29.90, "10OZ of Striploin accompanied with rustic potatoes and a watercress and cherry tomato salad.", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Barriga de Porco", 24.90, "10 OZ of Pork Belly accompanied with rustic potatoes and coleslaw..", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Suco de Laranja", 5.00, "Suco de laranja Natural", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Coca cola", 3.00, "Refrigerante", false, 4);

insert into user (name, email, password, date_created) value ("Jay Gibran", "jaydeliveryfoodapi@gmail.com", "123456789", utc_timestamp);
insert into user(name, email, password, date_created) value ("Emiriam Caroline", "emiriamdeliveryfoodapi@gmail.com", "987654321", utc_timestamp);
insert into user(name, email, password, date_created) value ("Maria Hosana", "mariadeliveryfoodapi@gmail.com", "123456", utc_timestamp);

insert into user_group(user_id, group_id) value (1, 1);
insert into user_group(user_id, group_id) value (1, 2);
insert into user_group(user_id, group_id) value (2, 1);

insert into restaurant_user_responsible(restaurant_id, user_id) value (1, 1);
insert into restaurant_user_responsible(restaurant_id, user_id) value (1, 2);
insert into restaurant_user_responsible(restaurant_id, user_id) value (2, 1);
insert into restaurant_user_responsible(restaurant_id, user_id) value (2, 2);
insert into restaurant_user_responsible(restaurant_id, user_id) value (3, 1);
insert into restaurant_user_responsible(restaurant_id, user_id) value (3, 2);
insert into restaurant_user_responsible(restaurant_id, user_id) value (4, 1);
insert into restaurant_user_responsible(restaurant_id, user_id) value (4, 2);

insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, sub_total, fee_delivery, total)
values (1, "7e5da0a6-eb51-4497-97ae-1f67388e6251", 4, 1, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2',
        'CREATED', utc_timestamp, 34.90, 11, 45.90);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (1, 1, 8, 1, 5.00, 5.00, "No sugar");

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (2, 1, 4, 1, 29.90, 29.90, "Middle ");

insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, sub_total, fee_delivery, total)
values (2, "c2e06edf-a312-4e53-9190-6dd5a533bc82", 1, 1, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2',
        'CREATED', utc_timestamp, 12, 10, 22);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (3, 2, 1, 1, 12, 12, "Please hot soup and add napkins");

