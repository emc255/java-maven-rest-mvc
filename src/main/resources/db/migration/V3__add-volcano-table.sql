DROP TABLE if EXISTS volcano;

CREATE TABLE volcano (
    id VARCHAR(36) NOT NULL,
    version INTEGER,
    name VARCHAR(255),
    country VARCHAR(255),
    region VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    elevation INTEGER,
    type VARCHAR(255),
    status VARCHAR(255),
    created_date datetime(6),
    update_date datetime(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;