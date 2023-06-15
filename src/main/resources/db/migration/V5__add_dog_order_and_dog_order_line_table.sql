DROP TABLE if EXISTS dog_order;
DROP TABLE if EXISTS dog_order_line;

CREATE TABLE dog_order (
    id VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    version INTEGER,
    created_date datetime(6),
    update_date datetime(6),
    PRIMARY KEY (id)
    ) engine=InnoDB;

CREATE TABLE dog_order_line (
    id VARCHAR(36) NOT NULL,
    beer_id VARCHAR(36) NOT NULL,
    beer_order_id VARCHAR(36) NOT NULL,
    version INTEGER,
    order_quantity INTEGER,
    quantity_allocated INTEGER,
    created_date datetime(6),
    update_date datetime(6),
    PRIMARY KEY (id)
    ) engine=InnoDB;
