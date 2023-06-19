DROP TABLE IF EXISTS dog_order_shipment;

CREATE TABLE dog_order_shipment (
    id VARCHAR(36) NOT NULL,
    dog_order_id VARCHAR(36) NOT NULL,
    version INTEGER,
    tracking_number VARCHAR(50),
    created_date datetime(6),
    update_date datetime(6),
    PRIMARY KEY (id),
    CONSTRAINT dogOrderShipment_fk_dogOrderId FOREIGN KEY (dog_order_id) REFERENCES dog_order (id)
) ENGINE=InnoDB;

ALTER TABLE dog_order
    ADD COLUMN dog_order_shipment_id VARCHAR(36);

ALTER TABLE dog_order
    ADD CONSTRAINT dogOrder_fk_dogOrderShipmentId
        FOREIGN KEY (dog_order_shipment_id) REFERENCES dog_order_shipment (id);