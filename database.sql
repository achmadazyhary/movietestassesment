CREATE DATABASE assement_test;

USE assement_test;

CREATE Table movie 
(
    id int NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    description varchar(255),
    rating float,
    image varchar(255),
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (id)
) ENGINE InnoDB;

SELECT * FROM movie;