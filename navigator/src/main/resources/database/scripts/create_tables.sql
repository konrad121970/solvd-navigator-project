drop database if exists navigator_db;

create database if not exists navigator_db;

use navigator_db;

create table if not exists addresses
  (
     id              SERIAL,-- BIGINT UNSIGNED NOT NULL AUTOINCREMENT UNIQUE
     city            varchar(45) not null,
     street          varchar(45) not null,
     building_number varchar(45) not null,
     postal_code     varchar(45) not null,
     primary key (id)
  );

create table if not exists workshops
  (
     id          SERIAL,
     addresses_id bigint unsigned not null,
     name        varchar(45) not null,
     nip         varchar(45) not null,
     primary key (id),
     constraint fk_workshops_addresses foreign key (addresses_id) references
     addresses (id) on delete cascade on update no action
  );

CREATE TABLE Cities (
    id SERIAL, -- BIGINT UNSIGNED NOT NULL AUTOINCREMENT UNIQUE
    name VARCHAR(50) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

CREATE TABLE Routes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_city_id INT,
    end_city_id INT,
    distance INT NOT NULL,
    intermediate_cities VARCHAR(255),
    FOREIGN KEY (start_city_id) REFERENCES Cities(id),
    FOREIGN KEY (end_city_id) REFERENCES Cities(id)
);
