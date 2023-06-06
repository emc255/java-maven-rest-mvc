
    drop table if exists customer;

    drop table if exists dog;

    create table customer (
        version integer,
        created_date datetime(6),
        last_modified_date datetime(6),
        id varchar(36) not null,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create TABLE dog (
        dog_breed tinyint not null check (dog_breed between 0 and 3),
        price decimal(38,2) not null,
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        name varchar(50) not null,
        upc varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;
