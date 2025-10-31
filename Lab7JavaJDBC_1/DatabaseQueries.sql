SHOW DATABASES;

-- create a userdb database
CREATE DATABASE userdb;
USE userdb;

-- CREATE TABLES & COLUMNS
CREATE TABLE student(
	student_id INT PRIMARY KEY AUTO_INCREMENT,
	full_name VARCHAR(20),
 	age INT,
	college VARCHAR(50),
    phone VARCHAR(25),
    continent VARCHAR(25),
    experience VARCHAR(100)
);

-- displaying data present in the table (Read)
-- SELECT <columns/ *> FROM <table> ;
	SELECT * FROM student;

-- Create:
-- INSERT INTO <table> VALUES( <value1>, <value2>, <value3>, ... );
INSERT INTO student(full_name, age, college, phone, continent, experience) 
VALUES("Marcus", 24, "College of Engineering (COE)", "212-555-7890", "North America", "Coding, Gaming");

INSERT INTO student(full_name, age, college, phone, continent, experience) 
VALUES("Sophia", 26, "College of Science (COS)", "415-123-4567", "Europe", "Reading, Hiking");

INSERT INTO student(full_name, age, college, phone, continent, experience) 
VALUES("Raj", 25, "College of Engineering (COE)", "647-234-5678", "Asia", "Swimming, Photography");

INSERT INTO student(full_name, age, college, phone, continent, experience) 
VALUES("Amara", 23, "College of Science (COS)", "202-876-5432", "Africa", "Painting, Cooking");

INSERT INTO student(full_name, age, college, phone, continent, experience) 
VALUES("James", 29, "College of Engineering (COE)", "604-345-6789", "Australia", "Tennis, Music Production");

-- Update:
-- UPDATE <table> SET <column> = <value> WHERE <column> = <value>
UPDATE student SET last_name = 'Rashford' WHERE first_name = 'Marcus';
-- Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column.  To disable safe mode, toggle the option in Preferences -> SQL Editor and reconnect.
-- This error occurs since the WHERE clause does not reference any primary key columns.

UPDATE student SET last_name = 'Rhodes' WHERE student_id = 2;
-- This updates the row successfullly.

-- delete a row
-- DELETE FROM <table_name> WHERE <column> = <value>;
DELETE FROM student WHERE student_id = 1;

-- Delete the entire table
-- DROP TABLE <table_name>
DROP TABLE student;