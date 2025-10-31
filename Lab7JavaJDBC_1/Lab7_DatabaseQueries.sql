SHOW DATABASES;

-- create a userdb database
CREATE DATABASE userdb;
USE userdb;

CREATE TABLE user(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(50),
    last_name VARCHAR(50),
 	age INT,
	email VARCHAR(50),
    phone VARCHAR(25),
    continent VARCHAR(25),
    hobby VARCHAR(100)
);

-- displaying data present in the table (Read)
-- SELECT <columns/ *> FROM <table> ;
	SELECT * FROM user;

-- Create:
-- INSERT INTO <table> VALUES( <value1>, <value2>, <value3>, ... );
INSERT INTO user(first_name, last_name, age, email, phone, continent, hobby) 
VALUES("Marcus",24, "john.doe@gmail.com", "212-555-7890", "North America", "Coding, Gaming");

INSERT INTO user(first_name, last_name, age, email, phone, continent, hobby) 
VALUES("Sophia", 26, "eavans@yahoo.com", "415-123-4567", "Europe", "Reading, Hiking");

INSERT INTO user(first_name, last_name, age, email, phone, continent, hobby) 
VALUES("Raj", 25, "tom202@gmail.com", "647-234-5678", "Asia", "Swimming, Photography");

INSERT INTO user(first_name, last_name, age, email, phone, continent, hobby) 
VALUES("Amara", 23, "claude.g@hotmail.com", "202-876-5432", "Africa", "Painting, Cooking");

INSERT INTO user(first_name, last_name, age, email, phone, continent, hobby) 
VALUES("James", 29, "Micah.j@gmail.com", "604-345-6789", "Australia", "Tennis, Music Production");

-- Update:
-- UPDATE <table> SET <column> = <value> WHERE <column> = <value>
UPDATE user SET last_name = 'Rashford' WHERE first_name = 'Marcus';
-- Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column.  To disable safe mode, toggle the option in Preferences -> SQL Editor and reconnect.
-- This error occurs since the WHERE clause does not reference any primary key columns.

UPDATE user SET last_name = 'Rhodes' WHERE user = 2;
-- This updates the row successfullly.

-- delete a row
-- DELETE FROM <table_name> WHERE <column> = <value>;
DELETE FROM user WHERE user_id = 1;

-- Delete the entire table
-- DROP TABLE <table_name>
DROP TABLE user;