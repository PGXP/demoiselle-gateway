﻿
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
  dia date,
  caminho character varying(512),
  CONSTRAINT hit_pkey PRIMARY KEY (id)
);

-- DROP VIEW public.resume;

CREATE VIEW public.resume as
select usuario id, dia, count(usuario) qtde
from  public.hit
group by usuario, dia
order by usuario, dia;

INSERT INTO public.client(id, usuario, dias, qtde, caminho)
 VALUES (0, '966fc202-f2ef-423f-b64b-314274ca68b6', 1, 100, '/ceps');