-- Database: video_products_app
-- DROP DATABASE video_products_app;

CREATE DATABASE video_products_app
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE director_film
(
    id SERIAL PRIMARY KEY NOT NULL,
	director_id INTEGER,
    	film_id INTEGER
);
------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE director
(
    id SERIAL PRIMARY KEY NOT NULL,
   first_name character varying (30) NOT NULL,
    last_name character varying (30) NOT NULL,
    birth_date date);
------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE film
(
    id SERIAL PRIMARY KEY NOT NULL,
    name character varying (100) NOT NULL,
    release_date date,
	genre character varying (200) NOT NULL
);
