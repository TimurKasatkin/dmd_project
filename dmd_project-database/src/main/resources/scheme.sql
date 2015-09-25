CREATE TABLE Authors (
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR NOT NULL,
  last_name  VARCHAR,
  CONSTRAINT author_full_name_uniq UNIQUE (first_name, last_name)
);

CREATE TABLE Science_Areas (
  id   SERIAL PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE Journals (
  id   SERIAL PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE Articles (
  id       SERIAL PRIMARY KEY,
  title    VARCHAR NOT NULL,
  publtype VARCHAR,
  url      VARCHAR NOT NULL,
  journal_id INT REFERENCES Journals (id),
  year     INT,
  volume     VARCHAR,
  number     VARCHAR
);

CREATE TABLE Article_Author (
  article_id INT REFERENCES Articles (id),
  author_id  INT REFERENCES Authors (id),
  CONSTRAINT article_author_pk PRIMARY KEY (article_id, author_id)
);

CREATE TABLE Article_Area (
  article_id INT REFERENCES Articles (id),
  area_id    INT REFERENCES Science_Areas (id),
  CONSTRAINT article_area_pk PRIMARY KEY (article_id, area_id)
);

CREATE TABLE Users (
  id       SERIAL PRIMARY KEY,
  "login"  VARCHAR(30) UNIQUE NOT NULL,
  password VARCHAR            NOT NULL,
  email    VARCHAR
);