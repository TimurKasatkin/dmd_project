CREATE TABLE Articles (
  id    SERIAL PRIMARY KEY,
  title VARCHAR NOT NULL,
  year  INT,
  publtype VARCHAR,
  url   VARCHAR NOT NULL
);

CREATE TABLE Authors (
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR NOT NULL,
  last_name  VARCHAR NOT NULL
);

CREATE TABLE Science_Areas (
  id   SERIAL PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE Journals (
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE  Article_Journal (
  article_id INT REFERENCES Articles (id),
  journal_id INT REFERENCES Journals (id),
  volume     VARCHAR,
  number     VARCHAR,
  CONSTRAINT article_journal_pk PRIMARY KEY (article_id, journal_id)
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
)