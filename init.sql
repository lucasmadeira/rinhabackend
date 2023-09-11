DROP DATABASE IF EXISTS pessoas;

CREATE DATABASE pessoas
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


CREATE EXTENSION pg_trgm;

CREATE TABLE IF NOT EXISTS public.pessoa
(
    id uuid NOT NULL,
    apelido character varying(32) COLLATE pg_catalog."default" NOT NULL,
    facet text COLLATE pg_catalog."default",
    nascimento date NOT NULL,
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pessoa_pkey PRIMARY KEY (id),
    CONSTRAINT uk_apelido UNIQUE (apelido)
);
CREATE TABLE IF NOT EXISTS public.pessoa_stack
(
    pessoa_id uuid NOT NULL,
    stack character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT fk5tx646jtpj911sq4o0i2k2gai FOREIGN KEY (pessoa_id)
        REFERENCES public.pessoa (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE INDEX pessoas_search_index_idx ON pessoa USING gin (facet gin_trgm_ops);