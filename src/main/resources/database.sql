create table serveur
(
    id serial not null
        constraint serveur_pk
            primary key,
    prenom varchar,
    nom varchar
);

alter table serveur owner to postgres;

create table tableref
(
    id serial not null
        constraint table_pk
            primary key,
    nomtable varchar
);

alter table tableref owner to postgres;

create table carte
(
    id serial not null
        constraint carte_pk
            primary key,
    plat varchar,
    pu double precision
);

alter table carte owner to postgres;

create table facture
(
    id serial not null
        constraint facture_pk
            primary key,
    numero serial not null,
    tableid integer
        constraint facture_table_id_fk
            references tableref,
    nbconvives integer,
    serveurid integer
        constraint facture_serveur_id_fk
            references serveur,
    etat char
);

alter table facture owner to postgres;

create unique index facture_numero_uindex
	on facture (numero);

create table commandes
(
    id serial not null
        constraint commandes_pk
            primary key,
    numfactureid integer
        constraint commandes_facture_numero_fk
            references facture (numero),
    platid integer
        constraint commandes_carte_id_fk
            references carte,
    quantité integer
);

alter table commandes owner to postgres;

