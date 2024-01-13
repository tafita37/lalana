-- Etat de pk
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(1, 'Potika enjana be');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(2, 'Potika be');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(3, 'Potika');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(4, 'Simba enjana be');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(5, 'Simba be');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(6, 'Simba');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(7, 'Maloto enjana be');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(8, 'Maloto be');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(9, 'Maloto');
    insert into etat_pk(niveau_etat_pk, nom_etat_pk) values(10, 'Parfait');

-- Traitement
    insert into traitement(nom_traitement, id_objectif, id_debut, id_fin, prix_traitement) values('Nettoyage', 10, 7, 9, 1000);
    insert into traitement(nom_traitement, id_objectif, id_debut, id_fin, prix_traitement) values('Reparation', 7, 4, 6, 2000);
    insert into traitement(nom_traitement, id_objectif, id_debut, id_fin, prix_traitement) values('Boucher', 4, 1, 3, 3000);

-- Priorite
    insert into priorite(id_priorite, nom_priorite) values(1, 'Beaute');
    insert into priorite(id_priorite, nom_priorite) values(2, 'Economique');