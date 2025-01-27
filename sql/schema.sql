CREATE TABLE Client(
   id_client SERIAL,
   nom VARCHAR(50) ,
   num VARCHAR(50) ,
   PRIMARY KEY(id_client)
);

CREATE TABLE Marque(
   id_marque SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(id_marque)
);

CREATE TABLE categorie_modele(
   id_categorie_modele SERIAL,
   nom_type VARCHAR(50) ,
   PRIMARY KEY(id_categorie_modele)
);

CREATE TABLE Sexe(
   id_sexe SERIAL,
   nom VARCHAR(50),
   PRIMARY KEY(id_sexe)
);

CREATE TABLE Modele(
   id_modele SERIAL,
   nom VARCHAR(50) ,
   id_marque INTEGER NOT NULL,
   id_categorie_modele INTEGER NOT NULL,
   PRIMARY KEY(id_modele),
   FOREIGN KEY(id_marque) REFERENCES Marque(id_marque),
   FOREIGN KEY(id_categorie_modele) REFERENCES categorie_modele(id_categorie_modele)
);

CREATE TABLE Ordinateur(
   id_ordinateur SERIAL,
   id_serie VARCHAR(50) ,
   id_modele INTEGER NOT NULL,
   id_client INTEGER NOT NULL,
   PRIMARY KEY(id_ordinateur),
   FOREIGN KEY(id_modele) REFERENCES Modele(id_modele),
   FOREIGN KEY(id_client) REFERENCES Client(id_client)
);

CREATE TABLE Type_composant(
   id_type_composant SERIAL,
   nom_type VARCHAR(50) ,
   PRIMARY KEY(id_type_composant)
);

CREATE TABLE Technicien(
   id_technicien SERIAL,
   nom VARCHAR(50) ,
   id_sexe INTEGER NOT NULL DEFAULT 1,
   PRIMARY KEY(id_technicien),
   FOREIGN KEY(id_sexe) REFERENCES Sexe(id_sexe)
);

CREATE TABLE Reparation(
   id_reparation SERIAL,
   date_debut TIMESTAMP,
   date_fin TIMESTAMP,
   descri VARCHAR(100) ,
   prix_main_doeuvre NUMERIC(15,2)  ,
   id_technicien INTEGER NOT NULL,
   id_ordinateur INTEGER NOT NULL,
   PRIMARY KEY(id_reparation),
   FOREIGN KEY(id_technicien) REFERENCES Technicien(id_technicien),
   FOREIGN KEY(id_ordinateur) REFERENCES Ordinateur(id_ordinateur)
);

CREATE TABLE Composant(
   id_composant SERIAL,
   nom VARCHAR(50) ,
   prix NUMERIC(15,2)  ,
   id_type_composant INTEGER NOT NULL,
   PRIMARY KEY(id_composant),
   FOREIGN KEY(id_type_composant) REFERENCES Type_composant(id_type_composant)
);

CREATE TABLE Stock(
   id_stock SERIAL,
   entree INTEGER,
   sortie INTEGER,
   date_mvt TIMESTAMP,
   quantite INTEGER,
   id_composant INTEGER NOT NULL,
   PRIMARY KEY(id_stock),
   FOREIGN KEY(id_composant) REFERENCES Composant(id_composant)
);

CREATE TABLE Reparation_composant(
   id_composant INTEGER,
   id_reparation INTEGER,
   probleme VARCHAR(100),
   PRIMARY KEY(id_composant, id_reparation),
   FOREIGN KEY(id_composant) REFERENCES Composant(id_composant),
   FOREIGN KEY(id_reparation) REFERENCES Reparation(id_reparation)
);

CREATE TABLE Modele_composant(
   id_modele INTEGER,
   id_composant INTEGER,
   PRIMARY KEY(id_modele, id_composant),
   FOREIGN KEY(id_modele) REFERENCES Modele(id_modele),
   FOREIGN KEY(id_composant) REFERENCES Composant(id_composant)
);

CREATE TABLE Composant_recommande(
   id_recommandation INTEGER,
   id_composant INTEGER,
   date DATE,
   motif VARCHAR(200),
   PRIMARY KEY(id_recommandation),
   FOREIGN KEY(id_composant) REFERENCES Composant(id_composant)
);

CREATE TABLE Commission (
    id_commission SERIAL PRIMARY KEY,
    pourcentage NUMERIC(5,2),
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE VIEW v_client_reparation_date AS
SELECT DISTINCT 
   c.id_client,
   c.nom,
   c.num,
   TO_CHAR(r.date_debut, 'DD/MM/YYYY') AS date_debut
FROM client c
JOIN ordinateur o ON c.id_client = o.id_client
JOIN reparation r ON o.id_ordinateur = r.id_ordinateur;

CREATE OR REPLACE VIEW v_commission AS
SELECT 
    t.id_technicien,
    t.nom as nom_technicien,
    r.id_reparation,
    r.date_debut,
    r.date_fin,
    COALESCE(r.prix_main_doeuvre, 0) as prix_main_doeuvre,
    c.pourcentage,
    ROUND(COALESCE(r.prix_main_doeuvre, 0) * c.pourcentage / 100, 2) as commission
FROM technicien t
LEFT JOIN reparation r ON t.id_technicien = r.id_technicien
CROSS JOIN commission c
WHERE c.id_commission = (SELECT id_commission FROM commission ORDER BY date_modification DESC LIMIT 1);
