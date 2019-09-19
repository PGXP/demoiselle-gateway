-- DROP TABLE public.usuario;

CREATE TABLE public.usuario
(
  id uuid NOT NULL,
  email character varying(512) NOT NULL,
  nome character varying(512),
  senha character varying(512),
  foto character varying(512),
  perfil integer,
  ativo boolean DEFAULT false,
  CONSTRAINT usuario_pkey PRIMARY KEY (id)
);

-- DROP TABLE public.client;

CREATE TABLE public.client 
(
  id bigserial,
  usuario uuid,
  dias int,
  qtde bigint,
  caminho character varying(512),
  CONSTRAINT client_pkey PRIMARY KEY (id)
);

-- DROP TABLE public.hit;

CREATE TABLE public.hit
(
  id bigserial,
  usuario uuid,
  origem character varying(64),
  caminho character varying(512),
  data timestamp DEFAULT now(),
  CONSTRAINT hit_pkey PRIMARY KEY (id)
);

-- DROP VIEW public.resume;

CREATE OR REPLACE VIEW public.resume AS 
 SELECT hit.usuario AS id,
    date(hit.data) dia,
    count(hit.usuario) AS qtde
   FROM hit
  GROUP BY hit.usuario, date(hit.data)
  ORDER BY hit.usuario, date(hit.data);


INSERT INTO public.client(id, usuario, dias, qtde, caminho)
 VALUES (0, '966fc202-f2ef-423f-b64b-314274ca68b6', 1, 10000000, '/ceps');

-- DROP SEQUENCE public.hibernate_sequence;

CREATE SEQUENCE public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

INSERT INTO public.usuario(
            id, email, nome, senha, foto, perfil, ativo)
    VALUES ('97ea5e4f-c925-4c29-9519-9d1c8973530b', 'paulopinheiro777@gmail.com', 'PauloGladson', 'e10adc3949ba59abbe56e057f20f883e', '', 0, true);
