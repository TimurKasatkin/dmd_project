-- ALL ENTITIES

SELECT all_fiels
FROM table_name alias
WHERE alias.id = ?;

SELECT count(*) AS table_name_count
FROM table_name;

DELETE FROM table_name
WHERE id = ?;

SELECT all_fiels
FROM table_name alias
ORDER BY sort_by_field(ASC| DESC)
LIMIT max_count
OFFSET start_from;


-- ARTICLES
SELECT
  a.id       AS article_id,
  a.title    AS article_title,
  a.publtype AS article_publtype,
  a.url      AS article_url,
  a.year     AS article_year
FROM articles a
  JOIN article_journal aj ON a.id = aj.article_id
WHERE a.id = ?;

-- For now instead of writing all article's fields I would like to write simply 'a.*'.
SELECT a.*
FROM articles a
  JOIN article_journal aj ON a.id = aj.article_id
WHERE a.id = ?;

-- ~* case-insensitive regexp
SELECT a.*
FROM articles a
WHERE a.title ~* ?;

-- articles by keyword
SELECT a.*
FROM articles a
  JOIN article_keyword ak ON a.id = ak.article_id
  JOIN keywords k ON ak.keyword_id = k.id
WHERE k.word = ?;

SELECT a.*
FROM authors auth
  JOIN article_author aa ON auth.id = aa.author_id
  JOIN articles a ON aa.article_id = a.id
WHERE auth.id = ?;

SELECT a.*
FROM journals j
  JOIN article_journal aj ON j.id = aj.journal_id
  JOIN articles a ON a.id = aj.article_id
WHERE j.id = ?;

SELECT a.*
FROM conferences c
  JOIN article_conference ac ON c.id = ac.conference_id
  JOIN articles a ON ac.article_id = a.id
WHERE c.id = ?;

INSERT INTO articles (title, publtype, url, year) VALUES (?, ?, ?, ?);

UPDATE articles
SET id = ?, title = ?, publtype = ?, url = ?, year = ?
WHERE id = ?;

-- AUTHORS

INSERT INTO authors (first_name, last_name) VALUES (?, ?);

UPDATE authors
SET id = ?, first_name = ?, last_name = ?
WHERE id = ?;

SELECT auth.*
FROM authors auth
WHERE auth.first_name ~* ? OR auth.last_name ~* ?;

-- CONFERENCES

INSERT INTO conferences (name) VALUES (?);

UPDATE conferences
SET id = ?, name = ?
WHERE id = ?;

SELECT c.*
FROM conferences c
WHERE c.name ~* ?;

-- JOURNALS

INSERT INTO journals (name) VALUES (?);

UPDATE journals
SET id = ?, name = ?
WHERE id = ?;

SELECT j.*
FROM journals j
WHERE j.name ~* ?;

-- KEYWORDS

INSERT INTO keywords (word) VALUES (?);

UPDATE keywords
SET id = ?, word = ?
WHERE id = ?;

SELECT k.*
FROM keywords k
WHERE k.word ~* ?;

-- USERS

INSERT INTO users (login, password, email) VALUES (?, ?, ?);

UPDATE users
SET id = ?, login = ?, password = ?, email = ?, role = ?
WHERE id = ?;

SELECT u.*
FROM users u
WHERE u.login ~* ? OR u.email ~* ?;

-- ARTICLE_AUTHOR

-- ARTICLE_CONFERENCE

-- ARTICLE_JOURNAL

-- ARTICLE_KEYWORD
