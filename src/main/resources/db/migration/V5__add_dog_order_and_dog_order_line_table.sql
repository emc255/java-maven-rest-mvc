DROP TABLE IF EXISTS dog_order_line;
DROP TABLE IF EXISTS dog_order;

CREATE TABLE dog_order (
    id VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    version INTEGER,
    created_date datetime(6),
    update_date datetime(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
    ) engine=InnoDB;

CREATE TABLE dog_order_line (
    id VARCHAR(36) NOT NULL,
    dog_id VARCHAR(36) NOT NULL,
    dog_order_id VARCHAR(36) NOT NULL,
    version INTEGER,
    order_quantity INTEGER,
    quantity_allocated INTEGER,
    created_date datetime(6),
    update_date datetime(6),
    PRIMARY KEY (id),
    CONSTRAINT dogOrderLine_fk_dogId FOREIGN KEY (dog_id) REFERENCES dog(id),
    CONSTRAINT dogOrderLine_fk_dogOrderId FOREIGN KEY (dog_order_id) REFERENCES dog_order(id)
    ) engine=InnoDB;
