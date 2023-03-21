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
delete
from product_photo;

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
insert into cuisine(id, name) value (5, "Japan");
insert into cuisine(id, name) value (6, "Korean");
insert into cuisine(id, name) value (7, "French");
insert into cuisine(id, name) value (8, "Greek");
insert into cuisine(id, name) value (9, "Irish");

insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated, active) value ("Thai Gourmet", 10, 1, "Pearse Street", "29", "D02 PP 50", "Grand canal dock", 1, utc_timestamp, utc_timestamp, true);
insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated, active) value ("Thai Delivery", 9.5, 2, "Dame Street", "50", "D02 PP 150", "City Center", 1, utc_timestamp, utc_timestamp, true);
insert into restaurant(name, fee_delivery, cuisine_id, date_created, date_updated, active) value ("Tuk Tuk Indian Food", 15, 2, utc_timestamp, utc_timestamp, true);
insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated, active) value ("BAH33º THE AUTHENTIC GAUCHO BBQ", 11, 3, "Dawson St", "Unit 3-5", "D02 X272", "Royal Hibernian Way", 1, utc_timestamp, utc_timestamp, true);
insert into restaurant(name, fee_delivery, cuisine_id, address_street, address_number, address_air_code, address_neighborhood, address_city_id, date_created, date_updated, active) value ("Mamma Mia", 8, 4, "Grattan Street", "2", "D02 P891", "Dublin 2", 1, utc_timestamp, utc_timestamp, true);

insert into payment_method(description) value ("Credit card");
insert into payment_method(description) value ("Debit card");
insert into payment_method(description) value ("Cash");

insert into permission(id, name, description) value (1, "EDIT_RESTAURANTS", "Allow to edit restaurants");
insert into permission(id, name, description) value (2, "EDIT_PAYMENT_METHODS", "Allow to edit restaurants");
insert into permission(id, name, description) value (3, "EDIT_CITIES", "Allow to edit cities");
insert into permission(id, name, description) value (4, "EDIT_STATES", "Allow to edit states");
insert into permission(id, name, description) value (5, "QUERY_USERS_GROUPS_PERMISSIONS", "Allow to query users");
insert into permission(id, name, description) value (6, "EDIT_USERS_GROUPS_PERMISSIONS", "Allow to edit users");
insert into permission(id, name, description) value (7, "EDIT_CUISINES", "Allow to edit cuisines");
insert into permission(id, name, description) value (8, "QUERY_ORDERS", "Allow to query orders");
insert into permission(id, name, description) value (9, "MANAGE_ORDERS", "Allow to manager orders");
insert into permission(id, name, description) value (10, "GENERATE_REPORTS", "Allow to generate reports");

insert into group_(id, name) value (1, "Manager");
insert into group_(id, name) value (2, "Seller");
insert into group_(id, name) value (3, "Assistant");
insert into group_(id, name) value (4, "Creator");
insert into group_(id, name) value (5, "Cook");
insert into group_(id, name) value (6, "Driver");
insert into group_(id, name) value (7, "Secretary");

-- added all permissions to manager group
insert into group_permission(group_id, permission_id)
select 1, id
from permission;

-- added permissions to seller group
insert into group_permission(group_id, permission_id)
select 2, id
from permission
where name like 'QUERY_%';

insert into group_permission(group_id, permission_id)
select 2, id
from permission
where name = 'EDIT_RESTAURANTS';

-- added permissions to assistant group
insert into group_permission(group_id, permission_id)
select 3, id
from permission
where name like 'QUERY_%';

-- added permissions to creator group
insert into group_permission(group_id, permission_id)
select 4, id
from permission
where name like '%_RESTAURANTS'
   or name like '%_PRODUCTS';


insert into restaurant_payment_method (restaurant_id, payment_method_id) value (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (4, 3), (5, 1), (5, 2), (5, 3);

insert into product(name, price, description, active, restaurant_id) value ("Tom Yum Goong", 12, "Spicy Shrimp Soup",  true, 1);
insert into product(name, price, description, active, restaurant_id) value ("Tom Kha Kai", 10, "Spicy Green Papaya Salad", true, 2);
insert into product(name, price, description, active, restaurant_id) value ("Chicken Masala", 15, "It's a dish consisting of roasted marinated chicken chunks in spiced curry sauce", true, 3);
insert into product(name, price, description, active, restaurant_id) value ("Picanha Dry Aged", 29.90, "10OZ of traditional Brazilian Rump Cap accompanied with rustic potatoes and Caprese salad.", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Costela Gaúcha 12 horas", 29.90, "Juicy 12-Hour Slow-Cooked Beef Ribs accompanied by rustic potatoes and mixed salad.", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Bife de Chorizo", 29.90, "10OZ of Striploin accompanied with rustic potatoes and a watercress and cherry tomato salad.", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Barriga de Porco", 24.90, "10 OZ of Pork Belly accompanied with rustic potatoes and coleslaw..", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Suco de Laranja", 5.00, "Suco de laranja Natural", true, 4);
insert into product(name, price, description, active, restaurant_id) value ("Coca cola", 3.00, "Refrigerante", false, 4);
insert into product(name, price, description, active, restaurant_id) value ("Olive con pane", 8.50, "mixed Italian olives marinated with garlic oil served with toasted bread", true, 5);
insert into product(name, price, description, active, restaurant_id) value ("Bruschetta all’aglio", 9.0, "3 pieces of toasted bread with garlic oil", true, 5);
insert into product(name, price, description, active, restaurant_id) value ("Ravioli al ragú di salsiccia", 24, "Italian sausage ragù with tomato sauce, basil,red onion and parmesan shaving", true, 5);
insert into product(name, price, description, active, restaurant_id) value ("Pasta alla carbonara", 19.50, "pasta with crispy Italian pancetta, eggs,
parmesan, pecorino cheese and hint of black pepper", true, 5);
insert into product(name, price, description, active, restaurant_id) value ("Nero di troia ", 9.50, "The indigenous variety of our region has a deep ruby color. Reveals aromas of
ripe red fruits and notes of caramel and vanilla. Full bodied structure and velvety
texttured tannins are followed by long aftertaste. An ideal companion for grilled
red meat", true, 5);


insert into user (name, email, password, date_created) value ("Jay Gibran", "jaydeliveryfoodapi@gmail.com", "$2a$12$F5KFCZQH37Wy7SsLXMoo8uX3KcKXcsxtIARj6UUF/LF.NCgQluWDa", utc_timestamp);
insert into user (name, email, password, date_created) value ("Emiriam Caroline", "emiriamdeliveryfoodapi@gmail.com", "$2a$12$F5KFCZQH37Wy7SsLXMoo8uX3KcKXcsxtIARj6UUF/LF.NCgQluWDa", utc_timestamp);
insert into user (name, email, password, date_created) value ("Maria Hosana", "mariadeliveryfoodapi@gmail.com", "$2a$12$F5KFCZQH37Wy7SsLXMoo8uX3KcKXcsxtIARj6UUF/LF.NCgQluWDa", utc_timestamp);
insert into user (name, email, password, date_created) value ("Antonio", "antonio-owner@gmail.com", "$2a$12$F5KFCZQH37Wy7SsLXMoo8uX3KcKXcsxtIARj6UUF/LF.NCgQluWDa", utc_timestamp);
insert into user (name, email, password, date_created) value ("Debora", "debora-user@gmail.com", "$2a$12$F5KFCZQH37Wy7SsLXMoo8uX3KcKXcsxtIARj6UUF/LF.NCgQluWDa", utc_timestamp);

insert into user_group(user_id, group_id) value (1, 1);
insert into user_group(user_id, group_id) value (2, 2);
insert into user_group(user_id, group_id) value (3, 3);;

insert into restaurant_user_responsible(restaurant_id, user_id) value (1, 2);
insert into restaurant_user_responsible(restaurant_id, user_id) value (1, 4);
insert into restaurant_user_responsible(restaurant_id, user_id) value (2, 2);
insert into restaurant_user_responsible(restaurant_id, user_id) value (3, 1);
insert into restaurant_user_responsible(restaurant_id, user_id) value (3, 2);
insert into restaurant_user_responsible(restaurant_id, user_id) value (4, 2);

insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, sub_total, fee_delivery, total)
values (1, "7e5da0a6-eb51-4497-97ae-1f67388e6251", 4, 5, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2', 'CREATED', utc_timestamp, 34.90, 11, 45.90);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (1, 1, 8, 1, 5.00, 5.00, "No sugar");

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (2, 1, 4, 1, 29.90, 29.90, "Middle ");

insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, sub_total, fee_delivery, total)
values (2, "c2e06edf-a312-4e53-9190-6dd5a533bc82", 1, 1, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2', 'DELIVERED', utc_timestamp, 12, 10, 22);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (3, 2, 1, 1, 12, 12, "Please hot soup and add napkins");


insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, sub_total, fee_delivery, total)
values (3, "3d3ee5ee-fb51-4d84-9549-e88084ee5fb9", 2, 1, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2', 'DELIVERED', utc_timestamp, 10, 9.5, 19.5);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (4, 3, 2, 1, 10, 10, "Please not so spicy");

insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, sub_total, fee_delivery, total)
values (4, "632e4a4e-76ec-4acc-967c-fafc0d8aedcd", 5, 1, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2', 'DELIVERED', utc_timestamp, 42, 8, 50);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (5, 4, 10, 1, 8.50, 8.50, "");

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (6, 4, 12, 1, 24, 24, "");

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (7, 4, 14, 1, 9.50, 9.50, "");

insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, sub_total, fee_delivery, total)
values (5, "6d2a35ac-06d7-11ed-b939-0242ac120002", 5, 1, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2', 'DELIVERED', "2022-07-16", 42, 8, 50);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (8, 5, 10, 1, 8.50, 8.50, "");

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (9, 5, 12, 1, 24, 24, "");

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (10, 5, 14, 1, 9.50, 9.50, "");

insert into order_ (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_air_code,
                    address_street, address_number, address_additional, address_neighborhood,
                    status, date_created, date_confirmation, date_delivered, sub_total, fee_delivery, total)
values (6, "e41251af-fdbc-4a0a-b0e2-d9d89c07c5fe", 3, 1, 1, 1, 'DD02 PP50', 'Pearse Street', '100', 'Upper Street', 'D2', 'DELIVERED', "2022-07-16 00:00", "2022-07-16 00:30", "2022-07-16 02:00", 15, 15, 30);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, note)
values (11, 6, 3, 1, 15, 15, "Tasty");