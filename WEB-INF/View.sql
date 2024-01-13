-- Derniere route
    create or replace view v_last_route as select*from route where id_route=(select max(id_route) from route);

-- Derniere reparation
    create or replace view v_last_reparation as select*from reparation where id_reparation=(select max(id_reparation) from reparation);

-- Dent a traiter
    create or replace view v_reparation_pk as select pk.*, reparation_pk.id_reparation, reparation_pk.niveau_etat_pk from pk join reparation_pk on reparation_pk.id_pk=pk.id_pk;

-- Dent abimer traitement
    create or replace view v_pk_abime as select*from v_reparation_pk where niveau_etat_pk!=10;