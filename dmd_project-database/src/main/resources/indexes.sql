CREATE INDEX ON articles USING BTREE (title);

CREATE INDEX ON authors USING BTREE (first_name);
CREATE INDEX ON authors USING BTREE (last_name);

CREATE INDEX ON journals USING BTREE (name);
CREATE INDEX ON conferences USING BTREE (name);
CREATE INDEX ON keywords USING BTREE (word);
