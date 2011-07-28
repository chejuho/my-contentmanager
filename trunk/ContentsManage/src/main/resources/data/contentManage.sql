
/* Drop Tables */

DROP TABLE contents;
DROP TABLE destination;
DROP TABLE destination_db_info;
DROP TABLE table_info;
DROP TABLE users;




/* Create Tables */

CREATE TABLE contents
(
	id integer NOT NULL,
	content_name varchar(64) NOT NULL,
	table_info_id integer NOT NULL,
	PRIMARY KEY (id)
);

DROP TABLE destination;
CREATE TABLE destination
(
	id integer NOT NULL,
	content_id integer NOT NULL,
	destination_name varchar(64) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE destination_db_info
(
	id integer NOT NULL AUTO_INCREMENT,
	destination_id integer NOT NULL,
	url varchar(64) NOT NULL,
	db_id varchar(64) NOT NULL,
	db_password varchar(64) NOT NULL,
	environment_id integer NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE table_info
(
	id integer NOT NULL UNIQUE AUTO_INCREMENT,
	physical_name varchar(64) NOT NULL,
	logical_name varchar(64) NOT NULL,
	content_id integer,
	PRIMARY KEY (id)
);


CREATE TABLE users
(
	id integer NOT NULL AUTO_INCREMENT,
	password char(41) NOT NULL,
	username varchar(64) NOT NULL,
	PRIMARY KEY (id)
);



