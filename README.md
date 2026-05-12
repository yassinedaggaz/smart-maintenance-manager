# 🏢 Smart Maintenance Manager

## 1. Description du projet
**Smart Maintenance Manager** est une plateforme web moderne conçue pour la gestion complète des opérations de maintenance. Ce projet permet d'optimiser le suivi du matériel et des équipes techniques. Ses fonctionnalités principales incluent :
- **Gestion des équipements** : Suivi de l'état, de l'historique et des caractéristiques du matériel.
- **Signalement des pannes** : Déclaration, catégorisation et suivi des incidents.
- **Gestion des techniciens** : Administration des profils, compétences et disponibilités.
- **Planification des interventions** : Affectation des techniciens aux pannes et suivi des coûts.
- **Sécurité et Authentification** : Système de connexion sécurisé avec des rôles (Admin, Technicien).

## 2. Technologies utilisées
Ce projet suit une architecture full-stack robuste et moderne :

**Backend :**
- **Java 17** & **Spring Boot 3** : Framework principal pour l'API REST.
- **Spring Data JPA / Hibernate** : Accès et manipulation des données.
- **Spring Security & JWT** : Sécurisation des endpoints et authentification stateless.
- **PostgreSQL** : Base de données relationnelle.
- **Swagger / OpenAPI** : Documentation interactive de l'API.

**Frontend :**
- **Angular 17+** : Framework Single Page Application (SPA).
- **TypeScript** & **RxJS** : Programmation typée et réactive.
- **Angular Material** : Bibliothèque de composants graphiques.

**DevOps :**
- **Docker** & **Docker Compose** : Conteneurisation pour faciliter le déploiement.
- **Maven** : Gestionnaire de dépendances Java.

---

## 3. Instructions d'installation et d'exécution

### Option A : Déploiement via Docker (Recommandé)
C'est la méthode la plus simple, car elle installe et lance automatiquement la base de données et le backend de manière isolée.

1. Assurez-vous d'avoir **Docker** et **Docker Compose** installés sur votre machine.
2. Ouvrez un terminal à la racine du projet et lancez la commande suivante :
   ```bash
   docker-compose up -d --build
   ```
3. L'API Backend sera accessible sur : `http://localhost:9090`
4. La documentation Swagger de l'API sera disponible sur : `http://localhost:9090/swagger-ui.html`

### Option B : Installation Manuelle (Développement Local)

**Prérequis :** Java 17, Maven, Node.js 18+ et PostgreSQL.

1. **Configuration de la Base de données :**
   - Créez une base de données PostgreSQL nommée `smart_maintenance_db`.
   - Utilisateur attendu : `yassine` / Mot de passe : `daggaz` (ces accès peuvent être modifiés dans le fichier `src/main/resources/application.yml`).
   - Exécutez le script fourni `database-schema.sql` pour initialiser les tables de la base de données.

2. **Lancement du Backend (Spring Boot) :**
   - Ouvrez un terminal à la racine du projet et exécutez les commandes suivantes pour compiler et lancer l'application :
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```
   - *L'API est maintenant disponible sur `http://localhost:9090`.*

3. **Lancement du Frontend (Angular) :**
   - Ouvrez un **nouveau terminal**, naviguez dans le dossier `frontend` et démarrez le serveur de développement :
   ```bash
   cd frontend
   npm install
   npm start
   ```
   - *L'application web (interface utilisateur) est maintenant accessible sur `http://localhost:4200`.*
