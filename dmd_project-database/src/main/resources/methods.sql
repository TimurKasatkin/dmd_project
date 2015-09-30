-- METHODS TO QUERY RELATED ARTICLES

-- Find conference articles by conference name
CREATE OR REPLACE FUNCTION
  articles_by_conference(conf_name VARCHAR)
  RETURNS SETOF articles AS $$
WITH cid AS (SELECT id
             FROM conferences
             WHERE name = conf_name)
SELECT a
FROM articles AS a, article_conference AS ac, cid
WHERE a.publtype = 'conference_article'
      AND a.id = ac.article_id
      AND ac.conference_id = cid.id
$$ LANGUAGE SQL;

-- Find journal articles by journal name
CREATE OR REPLACE FUNCTION
  articles_by_journal(journal_name VARCHAR)
  RETURNS SETOF articles AS $$
WITH jid AS (SELECT id
             FROM journals
             WHERE name = journal_name)
SELECT a
FROM articles AS a, article_journal AS aj, jid
WHERE a.publtype = 'journal_article'
      AND a.id = aj.article_id
      AND aj.journal_id IN (jid.id)
$$ LANGUAGE SQL;

-- Find articles with given keyword.
-- Second argument define whether search article
-- which have all of given keyword
-- or also include those which have only some from them.
-- Default is TRUE (have all keywords)
CREATE OR REPLACE FUNCTION articles_by_keywords(
  keys VARCHAR [], contains_all BOOL DEFAULT TRUE)
  RETURNS SETOF articles AS
  $BODY$
  BEGIN
    IF contains_all
    THEN
      RETURN QUERY (
        SELECT DISTINCT a.*
        FROM articles a, article_keyword ak
        WHERE a.id = ak.article_id
              AND ak.article_id
                  IN (SELECT ak.article_id AS id
                      FROM article_keyword ak
                      WHERE ak.keyword_id
                            IN (SELECT k.id
                                FROM keywords k
                                WHERE k.word = ANY (keys))
                      GROUP BY ak.article_id
                      HAVING count(*)
                             = array_length(keys, 1)));
    ELSE
      RETURN QUERY (
        SELECT DISTINCT a.*
        FROM articles AS a,
          article_keyword AS ak
        WHERE a.id = ak.article_id
              AND ak.keyword_id
                  IN (SELECT id
                      FROM keywords
                      WHERE word = ANY (keys)));
    END IF;
  END
  $BODY$ LANGUAGE plpgsql;

-- Find articles by author's full name
CREATE OR REPLACE FUNCTION articles_by_author(
  firstname VARCHAR, lastname VARCHAR)
  RETURNS SETOF articles AS $$
WITH aids AS (SELECT id
              FROM authors a
              WHERE a.first_name = firstname
                    AND a.last_name = lastname)
SELECT art
FROM articles art, article_author arta, aids
WHERE art.id = arta.article_id
      AND arta.author_id IN (aids.id)
$$ LANGUAGE SQL;

-- METHODS TO SORT PAPERS

-- Query articles and sort them by keys count.
-- Last column of result is key_count
-- ARGS:
-- is_asc - ascending sort or not
-- limit - max count of result records
CREATE OR REPLACE FUNCTION articles_sorted_by_key_count(
  is_asc BOOL DEFAULT TRUE, "limit" BIGINT DEFAULT 0)
  RETURNS
    TABLE(id INT, title VARCHAR, publtype VARCHAR,
    url VARCHAR, year INT, key_count BIGINT) AS
  $BODY$
  DECLARE
    arts_query VARCHAR;
  BEGIN
    arts_query := 'SELECT a.*,
          count(*) as keys_count
        FROM articles a,article_keyword ak
            WHERE a.id = ak.article_id
        GROUP BY a.id
        ORDER BY count(*) ';
    IF NOT is_asc
    THEN
      arts_query:=arts_query || 'DESC ';
    END IF;
    IF NOT "limit" = 0
    THEN
      arts_query:=arts_query || 'LIMIT ' || "limit";
    END IF;
    RETURN QUERY EXECUTE arts_query;
  END
  $BODY$ LANGUAGE plpgsql;

-- Query articles sorted by
-- ARGS:
-- column_name - name of column
--    by which sort
-- is_asc - ascending sort or not
-- limit - max count of result records
CREATE OR REPLACE FUNCTION articles_sorted_by(
  column_name VARCHAR, is_asc BOOL DEFAULT TRUE,
  "limit"     BIGINT DEFAULT 0)
  RETURNS SETOF articles AS
  $BODY$
  DECLARE
    arts_query VARCHAR;
  BEGIN
    arts_query := 'SELECT * FROM articles a
          ORDER BY a.' || column_name || ' ';
    IF NOT is_asc
    THEN
      arts_query := arts_query || 'DESC';
    END IF;
    IF NOT "limit" = 0
    THEN
      arts_query := arts_query || 'LIMIT ' || "limit";
    END IF;
    RETURN QUERY EXECUTE arts_query;
  END
  $BODY$ LANGUAGE plpgsql;