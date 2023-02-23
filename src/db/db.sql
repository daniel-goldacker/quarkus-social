CREATE TABLE public.users (
	id int8 NOT NULL DEFAULT nextval('user_id_seq'::regclass),
	"name" varchar(100) NOT NULL,
	age int4 NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);