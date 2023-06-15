DROP TABLE if EXISTS customer;
DROP TABLE if EXISTS dog;

CREATE TABLE customer (
    version INTEGER,
    created_date datetime(6),
    update_date datetime(6),
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    ) engine=InnoDB;

CREATE TABLE dog (
    breed VARCHAR(100),
    price DECIMAL(38,2) NOT NULL,
    quantity_on_hand INTEGER,
    version INTEGER,
    created_date datetime(6),
    update_date datetime(6),
    id VARCHAR(36) NOT NULL,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(50),
    sex VARCHAR(50),
    upc VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
    ) engine=InnoDB;
