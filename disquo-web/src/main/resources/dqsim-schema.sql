--DROP ALL OBJECTS;

CREATE SEQUENCE IF NOT EXISTS SEQ_USER START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS SEQ_CATEGORY START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS SEQ_THREAD START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS SEQ_POST START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS DQ_USER (
	ID BIGINT NOT NULL DEFAULT NEXTVAL('SEQ_USER') PRIMARY KEY, 
	USERNAME VARCHAR(64) NOT NULL, 
	EMAIL VARCHAR(64) NOT NULL,
	ACCESS_TOKEN VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS DQ_APPLICATION (
	API_KEY VARCHAR(64) NOT NULL PRIMARY KEY,
	SECRET_KEY VARCHAR(64) NOT NULL UNIQUE,
	ACCESS_TOKEN VARCHAR(64) NOT NULL UNIQUE,
	FOUNDER BIGINT NOT NULL,
	FOREIGN KEY(FOUNDER) REFERENCES DQ_USER(ID)
);

CREATE TABLE IF NOT EXISTS DQ_FORUM (
	ID VARCHAR(64) PRIMARY KEY,
	NAME VARCHAR(64) NOT NULL,
	FOUNDER BIGINT NOT NULL,
	STATE INT NOT NULL,
	JSON VARCHAR(40000) NOT NULL,
	FOREIGN KEY(FOUNDER) REFERENCES DQ_USER(ID)
);

CREATE TABLE IF NOT EXISTS DQ_CATEGORY (
	ID BIGINT NOT NULL DEFAULT NEXTVAL('SEQ_CATEGORY') PRIMARY KEY, 
	ORDERX INT NOT NULL,
	TITLE VARCHAR(64) NOT NULL,
	FORUM VARCHAR(64) NOT NULL,
	ISDEFAULT BOOLEAN NOT NULL,
	FOREIGN KEY(FORUM) REFERENCES DQ_FORUM(ID)
);

CREATE TABLE IF NOT EXISTS DQ_THREAD (
	ID BIGINT NOT NULL DEFAULT NEXTVAL('SEQ_THREAD') PRIMARY KEY,
	FORUM VARCHAR(64) NOT NULL,
	CATEGORY BIGINT NOT NULL,
	AUTHOR BIGINT NOT NULL,
	TITLE VARCHAR(64) NOT NULL,
	CREATED TIMESTAMP NOT NULL,
	STATE INT NOT NULL,
	JSON VARCHAR(40000) NOT NULL,
	FOREIGN KEY(FORUM) REFERENCES DQ_FORUM(ID),
	FOREIGN KEY(CATEGORY) REFERENCES DQ_CATEGORY(ID),
	FOREIGN KEY(AUTHOR) REFERENCES DQ_USER(ID)
);

CREATE TABLE IF NOT EXISTS DQ_POST(
	ID BIGINT NOT NULL DEFAULT NEXTVAL('SEQ_POST') PRIMARY KEY,
	PARENT BIGINT NOT NULL,
	THREAD BIGINT NOT NULL,
	FORUM VARCHAR(64) NOT NULL,
	AUTHOR BIGINT NOT NULL,
	CREATED TIMESTAMP NOT NULL,
	STATE INT NOT NULL,
	JSON VARCHAR(40000) NOT NULL,
	FOREIGN KEY(PARENT) REFERENCES DQ_POST(ID),
	FOREIGN KEY(THREAD) REFERENCES DQ_THREAD(ID),
	FOREIGN KEY(FORUM) REFERENCES DQ_FORUM(ID),
	FOREIGN KEY(AUTHOR) REFERENCES DQ_USER(ID)
);
