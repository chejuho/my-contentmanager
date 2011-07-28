
/* Drop Tables */

DROP TABLE columns;
DROP TABLE contents;
DROP TABLE destination;
DROP TABLE destination_db_info;
DROP TABLE sequence;
DROP TABLE table_info;
DROP TABLE users;




/* Create Tables */

CREATE TABLE columns
(
	id integer NOT NULL AUTO_INCREMENT,
	table_id integer NOT NULL,
	seq_id integer NOT NULL,
	physical_name varchar(64) NOT NULL,
	logical_name varchar(64) NOT NULL,
	description varchar(64) NOT NULL,
	index_sign tinyint NOT NULL,
	primary_key_sign tinyint NOT NULL,
	unique_key_sign tinyint NOT NULL,
	type varchar(64) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE contents
(
	id integer NOT NULL,
	content_name varchar(64) NOT NULL,
	PRIMARY KEY (id)
);


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


CREATE TABLE sequence
(
	id int NOT NULL
);


CREATE TABLE table_info
(
	id integer NOT NULL UNIQUE AUTO_INCREMENT,
	physical_name varchar(64) NOT NULL,
	logical_name varchar(64) NOT NULL,
	content_id integer NOT NULL,
	seq_id integer NOT NULL,
	PRIMARY KEY (id)
);

DROP TABLE users;
CREATE TABLE users
(
	id integer NOT NULL AUTO_INCREMENT,
	user_id varchar(64) NOT NULL UNIQUE,
	password char(41) NOT NULL,
	username varchar(64) NOT NULL,
	PRIMARY KEY (id)
);



