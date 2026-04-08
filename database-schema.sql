-- Smart Maintenance Manager Database Schema
-- PostgreSQL

-- Create Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'TECHNICIEN')),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Equipements table
CREATE TABLE equipements (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    etat VARCHAR(20) NOT NULL CHECK (etat IN ('EN_SERVICE', 'EN_PANNE', 'EN_MAINTENANCE')),
    date_acquisition DATE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Pannes table
CREATE TABLE pannes (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    categorie VARCHAR(50) NOT NULL CHECK (categorie IN ('ELECTRIQUE', 'MECANIQUE', 'HYDRAULIQUE', 'ELECTRONIQUE', 'LOGICIEL', 'AUTRE')),
    date_signalement DATE NOT NULL,
    equipement_id INTEGER NOT NULL REFERENCES equipements(id),
    statut VARCHAR(20) NOT NULL CHECK (statut IN ('SIGNALEEE', 'EN_COURS', 'RESOLUE')),
    remarques TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Techniciens table
CREATE TABLE techniciens (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telephone VARCHAR(20) NOT NULL,
    competences TEXT,
    disponibilite VARCHAR(20) NOT NULL CHECK (disponibilite IN ('DISPONIBLE', 'OCCUPE', 'EN_CONGE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Interventions table
CREATE TABLE interventions (
    id SERIAL PRIMARY KEY,
    date_debut TIMESTAMP NOT NULL,
    date_fin TIMESTAMP,
    statut VARCHAR(20) NOT NULL CHECK (statut IN ('PLANIFIEE', 'EN_COURS', 'TERMINEEE', 'ANNULEE')),
    cout NUMERIC(10,2),
    description TEXT,
    remarques TEXT,
    equipement_id INTEGER NOT NULL REFERENCES equipements(id),
    technicien_id INTEGER NOT NULL REFERENCES techniciens(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for performance
CREATE INDEX idx_pannes_equipement ON pannes(equipement_id);
CREATE INDEX idx_pannes_statut ON pannes(statut);
CREATE INDEX idx_interventions_equipement ON interventions(equipement_id);
CREATE INDEX idx_interventions_technicien ON interventions(technicien_id);
CREATE INDEX idx_interventions_statut ON interventions(statut);
CREATE INDEX idx_techniciens_email ON techniciens(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);

-- Insert sample data (optional)
-- You can uncomment these lines for testing

-- Insert users
-- INSERT INTO users (username, password, email, role) VALUES ('admin', 'hashed_password', 'admin@smartmaintenance.com', 'ADMIN');
-- INSERT INTO users (username, password, email, role) VALUES ('technicien01', 'hashed_password', 'tech01@smartmaintenance.com', 'TECHNICIEN');

-- Insert techniciens
-- INSERT INTO techniciens (nom, prenom, email, telephone, competences, disponibilite) 
-- VALUES ('Dupont', 'Jean', 'jean.dupont@smartmaintenance.com', '0601020304', 'Électricité, Mécanique', 'DISPONIBLE');





-- SELECT * FROM equipements;
-- SELECT * FROM pannes;
-- SELECT * FROM techniciens;
-- SELECT * FROM interventions;
-- SELECT * FROM users;