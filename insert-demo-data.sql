DELETE FROM users;
DELETE FROM interventions;
DELETE FROM pannes;
DELETE FROM techniciens;
DELETE FROM equipements;

INSERT INTO users (created_at, email, enabled, password, role, updated_at, username)
VALUES (NOW(), 'admin@example.com', true, 'yassinedagaz', 'ADMIN', NOW(), 'yassinedagaz'),
(NOW(), 'tech@example.com', true, 'tech', 'TECHNICIEN', NOW(), 'tech');

INSERT INTO equipements (id, nom, etat, date_acquisition, description, created_at, updated_at) VALUES 
(1, 'Tour CNC Haas VF-2', 'EN_SERVICE', '2020-03-15', 'Tour à commande numérique haute précision pour pièces métalliques.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Presse Hydraulique 500T', 'EN_PANNE', '2018-11-22', 'Presse industrielle pour emboutissage de tôle.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Four Industriel Thermolab', 'EN_MAINTENANCE', '2021-06-10', 'Four de traitement thermique montant jusqu''à 1200°C.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Chariot Élévateur Toyota', 'EN_SERVICE', '2019-01-05', 'Chariot élévateur électrique pour logistique interne, capacité 2T.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Convoyeur à Bande Ligne A', 'EN_SERVICE', '2022-09-01', 'Système de convoyage principal de la ligne de production.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER SEQUENCE equipements_id_seq RESTART WITH 6;

INSERT INTO techniciens (id, nom, prenom, email, telephone, date_embauche, competences, disponibilite, created_at, updated_at) VALUES 
(1, 'Martin', 'Lucas', 'lucas.martin@smartmaintenance.com', '06 12 34 56 78', '2020-01-15', 'Électrique, Électronique, Automatisme', 'DISPONIBLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Dubois', 'Sophie', 'sophie.dubois@smartmaintenance.com', '06 98 76 54 32', '2019-03-20', 'Mécanique, Hydraulique', 'OCCUPE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Bernard', 'Thomas', 'thomas.bernard@smartmaintenance.com', '07 11 22 33 44', '2021-07-10', 'Logiciel, Réseau industriel', 'EN_CONGE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER SEQUENCE techniciens_id_seq RESTART WITH 4;

INSERT INTO pannes (id, description, categorie, date_signalement, equipement_id, statut, remarques, created_at, updated_at) VALUES 
(1, 'Fuite d''huile détectée au niveau du vérin principal.', 'HYDRAULIQUE', '2026-05-10', 2, 'EN_COURS', 'Nécessite la commande d''un nouveau joint.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Le moteur surchauffe après 2h d''utilisation.', 'ELECTRIQUE', '2026-05-12', 4, 'SIGNALEEE', 'Vérifier le ventilateur de refroidissement.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Erreur logiciel E-404 sur l''écran de contrôle.', 'LOGICIEL', '2026-04-20', 1, 'RESOLUE', 'Mise à jour du firmware effectuée avec succès.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Bruit métallique anormal lors de la rotation.', 'MECANIQUE', '2026-05-14', 5, 'SIGNALEEE', 'Roulement potentiellement usé.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER SEQUENCE pannes_id_seq RESTART WITH 5;

INSERT INTO interventions (id, date_debut, date_fin, statut, cout, description, remarques, equipement_id, technicien_id, created_at, updated_at) VALUES 
(1, '2026-05-11 08:00:00', NULL, 'EN_COURS', 450.00, 'Remplacement du joint hydraulique et test de pression.', 'En attente de réception de la pièce.', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '2026-04-21 10:00:00', '2026-04-21 11:30:00', 'TERMINEEE', 120.00, 'Flashage du système et reboot.', 'Tout est OK.', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '2026-05-18 09:00:00', NULL, 'PLANIFIEE', 200.00, 'Inspection complète du système de refroidissement.', 'Planifié pour la semaine prochaine.', 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER SEQUENCE interventions_id_seq RESTART WITH 4;