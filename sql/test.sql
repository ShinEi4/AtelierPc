-- Données pour la table Client
INSERT INTO Client (nom, num) VALUES 
('Jean Dupont', '0612345678'),
('Marie Curie', '0698765432'),
('Paul Martin', '0712345678');

-- Données pour la table Marque
INSERT INTO Marque (nom) VALUES 
('Dell'),
('HP'),
('Lenovo');

-- Données pour la table Modele
INSERT INTO Modele (nom, id_marque) VALUES 
('Inspiron 15', 1),
('Pavilion 14', 2),
('ThinkPad X1', 3);

-- Données pour la table Ordinateur
INSERT INTO Ordinateur (id_serie, id_modele, id_client) VALUES 
('D12345', 1, 1),
('H67890', 2, 2),
('L54321', 3, 3);

-- Données pour la table Type_composant
INSERT INTO Type_composant (nom_type) VALUES 
('Processeur'),
('Mémoire RAM'),
('Carte Graphique');

-- Données pour la table Composant
INSERT INTO Composant (nom, prix, id_type_composant) VALUES 
('Intel Core i7', 250.00, 1),
('16GB DDR4', 80.00, 2),
('NVIDIA RTX 3060', 400.00, 3);

-- Données pour la table Stock
INSERT INTO Stock (entree, sortie, date_mvt, quantite, id_composant) VALUES 
(10, 0, '2025-01-01 10:00:00', 10, 1),
(20, 5, '2025-01-02 11:00:00', 15, 2),
(15, 10, '2025-01-03 12:00:00', 5, 3);

-- Données pour la table Technicien
INSERT INTO Technicien (nom) VALUES 
('Alice Lemoine'),
('Bob Durand'),
('Charlie Renault');

-- Données pour la table Reparation
INSERT INTO Reparation (date_debut, date_fin, descri, prix_main_doeuvre, id_technicien, id_ordinateur) VALUES 
('2025-01-01 09:00:00', '2025-01-01 12:00:00', 'Remplacement de la carte graphique', 100.00, 1, 1),
('2025-01-02 14:00:00', '2025-01-02 16:00:00', 'Ajout de mémoire RAM', 50.00, 2, 2),
('2025-01-03 10:00:00', '2025-01-03 13:00:00', 'Changement de processeur', 150.00, 3, 3);

-- Données pour la table Reparation_composant
INSERT INTO Reparation_composant (id_composant, id_reparation, probleme) VALUES 
(3, 1, 'Carte graphique endommagée'),
(2, 2, 'Manque de mémoire'),
(1, 3, 'Processeur surchauffe');

-- Données pour la table Modele_composant
INSERT INTO Modele_composant (id_modele, id_composant) VALUES 
(1, 1),
(2, 2),
(3, 3);
