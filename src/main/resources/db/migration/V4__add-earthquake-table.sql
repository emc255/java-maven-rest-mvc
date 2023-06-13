CREATE TABLE earthquake (
    id varchar(36) NOT NULL,
    `eruption-date` datetime(6),
    latitude DOUBLE,
    longitude DOUBLE,
    magnitude DOUBLE,
    PRIMARY KEY (id)
) ENGINE=InnoDB;