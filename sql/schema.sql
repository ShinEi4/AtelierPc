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

CREATE TABLE Modele(
   id_modele SERIAL,
   nom VARCHAR(50) ,
   id_marque INTEGER NOT NULL,
   PRIMARY KEY(id_modele),
   FOREIGN KEY(id_marque) REFERENCES Marque(id_marque)
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
   PRIMARY KEY(id_technicien)
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
