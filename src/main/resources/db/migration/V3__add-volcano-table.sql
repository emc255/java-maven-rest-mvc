CREATE TABLE volcano (
    id varchar(36) NOT NULL,
    name VARCHAR(255),
    country VARCHAR(255),
    region VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    elevation INTEGER,
    type VARCHAR(255),
    status VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;