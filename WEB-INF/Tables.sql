-- Database
    -- Lalana
        \c postgres
        drop database lalana;
        create database lalana;
        \c lalana

-- Tables
    -- Etat du pk
        create table etat_pk(
            niveau_etat_pk serial primary key, 
            nom_etat_pk varchar(30) unique
        );

    -- Traitement
        create table traitement(
            id_traitement serial primary key, 
            nom_traitement varchar(30), 
            id_objectif int references etat_pk(niveau_etat_pk), 
            id_debut int references etat_pk(niveau_etat_pk), 
            id_fin int references etat_pk(niveau_etat_pk),
            prix_traitement double precision
        );

    -- Route
        create table route(
            id_route varchar(20) primary key, 
            nom_route varchar(30) unique
        );

    -- Pk
        create table pk(
            id_pk serial primary key, 
            debut_pk int, 
            fin_pk int, 
            id_route varchar(20) references route(id_route)
        );

    -- Priorite
        create table priorite(
            id_priorite serial primary key, 
            nom_priorite varchar(30) unique
        );

    -- Reparation
        create table reparation(
            id_reparation serial primary key, 
            id_priorite serial references priorite(id_priorite), 
            id_route varchar(20) references route(id_route),
            date_heure_reparation timestamp, 
            budget double precision
        );

    -- Reparation de pk
        create table reparation_pk(
            id_reparation int references reparation(id_reparation), 
            id_pk int references pk(id_pk),
            niveau_etat_pk int references etat_pk(niveau_etat_pk)
        );