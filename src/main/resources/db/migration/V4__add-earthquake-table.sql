DROP TABLE if EXISTS earthquake;

CREATE TABLE earthquake (
    id VARCHAR(36) NOT NULL,
    eruption_date datetime(6),
    latitude DOUBLE,
    longitude DOUBLE,
    magnitude DOUBLE,
    PRIMARY KEY (id)
) ENGINE=InnoDB;