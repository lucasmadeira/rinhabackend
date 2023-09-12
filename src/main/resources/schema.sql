CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX IF NOT EXISTS pessoas_search_index_idx ON pessoa USING gin (facet gin_trgm_ops);