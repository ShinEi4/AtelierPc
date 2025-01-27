-- Insertion des sexes
INSERT INTO Sexe (nom) VALUES 
('Homme'),
('Femme');

-- Insertion des catégories de modèle
INSERT INTO categorie_modele (nom_type) VALUES 
('Laptop'),
('Desktop'),
('Workstation');

-- Insertion des marques
INSERT INTO Marque (nom) VALUES 
('HP'),
('Dell'),
('Lenovo'),
('Asus'),
('Acer');

-- Insertion des types de composants
INSERT INTO Type_composant (nom_type) VALUES 
('Processeur'),
('Mémoire RAM'),
('Disque Dur'),
('Carte Graphique'),
('Batterie');

-- Insertion des composants
INSERT INTO Composant (nom, prix, id_type_composant) VALUES 
('Intel i5 11th Gen', 250000, 1),
('Intel i7 12th Gen', 450000, 1),
('RAM DDR4 8GB', 120000, 2),
('RAM DDR4 16GB', 240000, 2),
('SSD 256GB', 180000, 3),
('SSD 512GB', 350000, 3),
('NVIDIA GTX 1650', 800000, 4),
('NVIDIA RTX 3060', 1500000, 4),
('Batterie HP 45W', 200000, 5),
('Batterie Dell 60W', 250000, 5);

-- Insertion des techniciens
INSERT INTO Technicien (nom, id_sexe) VALUES 
('Jean Dupont', 1),
('Marie Martin', 2),
('Pierre Durant', 1),
('Sophie Bernard', 2);

-- Insertion des clients
INSERT INTO Client (nom, num) VALUES 
('Rakoto Jean', '0341234567'),
('Rabe Marie', '0331234567'),
('Rasoa Pierre', '0321234567');

-- Insertion des modèles
INSERT INTO Modele (nom, id_marque, id_categorie_modele) VALUES 
('Pavilion 15', 1, 1),
('Latitude 5420', 2, 1),
('ThinkPad T14', 3, 1),
('ROG Strix', 4, 1),
('Predator', 5, 1);

-- Insertion des ordinateurs
INSERT INTO Ordinateur (id_serie, id_modele, id_client) VALUES 
('HP123456', 1, 1),
('DELL789012', 2, 2),
('LEN345678', 3, 3);

-- Insertion commission initiale
INSERT INTO Commission (pourcentage, date_modification) VALUES 
(10.00, CURRENT_TIMESTAMP);

-- Insertion des réparations
INSERT INTO Reparation (date_debut, date_fin, descri, prix_main_doeuvre, id_technicien, id_ordinateur) VALUES 
('2024-03-01 09:00:00', '2024-03-01 17:00:00', 'Remplacement RAM', 50000, 1, 1),
('2024-03-02 10:00:00', '2024-03-02 15:00:00', 'Installation SSD', 75000, 2, 2),
('2024-03-03 08:00:00', '2024-03-03 16:00:00', 'Remplacement batterie', 45000, 3, 3),
('2024-03-04 09:00:00', NULL, 'Réparation carte mère', 150000, 4, 1);

-- Insertion des composants utilisés dans les réparations
INSERT INTO Reparation_composant (id_composant, id_reparation, probleme) VALUES 
(3, 1, 'RAM défectueuse'),
(5, 2, 'Disque dur lent'),
(9, 3, 'Batterie ne charge plus');

-- Insertion des mouvements de stock
INSERT INTO Stock (entree, sortie, date_mvt, quantite, id_composant) VALUES 
(10, 0, '2024-03-01 08:00:00', 10, 3),
(0, 1, '2024-03-01 09:00:00', 9, 3),
(5, 0, '2024-03-02 08:00:00', 5, 5),
(0, 1, '2024-03-02 10:00:00', 4, 5),
(8, 0, '2024-03-03 08:00:00', 8, 9),
(0, 1, '2024-03-03 08:30:00', 7, 9);

-- Insertion des composants recommandés pour les modèles
INSERT INTO Modele_composant (id_modele, id_composant) VALUES 
(1, 1), (1, 3), (1, 5), (1, 9),
(2, 2), (2, 4), (2, 6), (2, 10),
(3, 1), (3, 4), (3, 6), (3, 9); 