DROP TABLE IF EXISTS dog_category;
DROP TABLE IF EXISTS category;


CREATE TABLE category (
    id VARCHAR(36) NOT NULL,
    version INTEGER,
    description VARCHAR(50),
    created_date datetime(6),
    update_date datetime(6),
    PRIMARY KEY (id)
    ) engine=InnoDB;

CREATE TABLE dog_category (
    dog_id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (dog_id, category_id),
    CONSTRAINT dogCategory_fk_dogId FOREIGN KEY (dog_id) REFERENCES dog (id),
    CONSTRAINT dogCategory_fk_categoryId FOREIGN KEY (category_id) REFERENCES category (id)
    ) engine=InnoDB;