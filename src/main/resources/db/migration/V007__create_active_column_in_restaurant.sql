alter table restaurant
    add active tinyint(1) not null default true;
update restaurant
set active = true;