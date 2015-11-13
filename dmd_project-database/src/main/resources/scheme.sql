CREATE TABLE Authors (
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR NOT NULL,
  last_name  VARCHAR,
  CONSTRAINT author_full_name_uniq
  UNIQUE (first_name, last_name)
);

CREATE TABLE Keywords (
  id   SERIAL PRIMARY KEY,
  word VARCHAR UNIQUE NOT NULL
);

CREATE TABLE Conferences (
  id   SERIAL PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE Journals (
  id   SERIAL PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE Articles (
  id       SERIAL PRIMARY KEY,
  title    VARCHAR UNIQUE NOT NULL,
  publtype VARCHAR        NOT NULL,
  url      VARCHAR        NOT NULL,
  year     INT
);

CREATE TABLE Article_Conference (
  article_id    INT PRIMARY KEY
    REFERENCES Articles (id)
    ON DELETE CASCADE,
  conference_id INT NOT NULL
    REFERENCES Conferences (id)
    ON DELETE CASCADE
);

CREATE TABLE Article_Journal (
  article_id INT PRIMARY KEY
    REFERENCES Articles (id)
    ON DELETE CASCADE,
  journal_id INT NOT NULL
    REFERENCES Journals (id)
    ON DELETE CASCADE,
  volume     VARCHAR,
  number     VARCHAR
);

CREATE TABLE Article_Author (
  article_id INT
    REFERENCES Articles (id)
    ON DELETE CASCADE,
  author_id  INT
    REFERENCES Authors (id)
    ON DELETE CASCADE,
  CONSTRAINT article_author_pk
  PRIMARY KEY (article_id, author_id)
);

CREATE TABLE Article_Keyword (
  article_id INT
    REFERENCES Articles (id)
    ON DELETE CASCADE,
  keyword_id INT
    REFERENCES Keywords (id)
    ON DELETE CASCADE,
  CONSTRAINT article_keyword_pk
  PRIMARY KEY (article_id, keyword_id)
);

CREATE TABLE Users (
  id       SERIAL PRIMARY KEY,
  "login"  VARCHAR(30) UNIQUE NOT NULL,
  password VARCHAR            NOT NULL,
  email    VARCHAR,
  role     VARCHAR            NOT NULL
);