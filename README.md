# Documentation de l'API de Gestion des Frigos

## Description de l'API
Cette API est une solution complète pour la gestion de **frigos**, conçue pour fonctionner dans un environnement sécurisé et conteneurisé. Elle permet aux administrateurs authentifiés de gérer des frigos via des endpoints protégés, de téléverser des fichiers XML contenant des informations sur les frigos, et d'enregistrer ces données en base de données. L'API est documentée et testable grâce à Swagger, et peut être facilement déployée avec Docker sur différents environnements.

---

## Fonctionnalités principales

### Gestion des frigos
L'API permet de gérer des frigos avec les attributs suivants :
- **`idFrigo`** : Identifiant unique généré automatiquement.
- **`contenue`** : Description ou contenu général du frigo (par exemple, "produits frais").
- **`contenanceMax`** : Capacité maximale en litres.
- **`contenanceMin`** : Capacité minimale nécessaire pour un fonctionnement optimal.
- **`humidite`** : Niveau d'humidité du frigo (en pourcentage).
- **`temperatureActuelle`** : Température actuelle (en degrés Celsius).
- **`temperatureCritique`** : Température critique à ne pas dépasser pour éviter les dommages.

### Importation de données via XML
- Téléversement de fichiers XML contenant des informations structurées sur les frigos.
- Extraction, validation et enregistrement des données en base de données.
- Gestion des erreurs pour les fichiers mal formés.

---

## Endpoints et sécurité

### Public
- **`POST /login`** :
   - Tout utilisateur peut se connecter avec son email et mot de passe pour générer un token JWT.
   - Seuls les utilisateurs ayant le rôle **Administrateur** peuvent accéder aux autres endpoints protégés.

### Endpoints protégés (réservés aux administrateurs)
- **`GET /api/frigos`** : Liste tous les frigos enregistrés en base.
- **`POST /api/frigos`** : Ajoute un nouveau frigo en base de données.
- **`POST /api/frigos/upload`** : Téléverse un fichier XML contenant des données sur les frigos pour les enregistrer en base.
- **`DELETE /api/frigos/{id}`** : Supprime un frigo identifié par son ID.

### Sécurité
1. **Authentification** :
   - Les utilisateurs se connectent via `/login`.
   - Un token JWT est généré après une authentification réussie.
2. **Autorisation** :
   - Seuls les administrateurs peuvent interagir avec les endpoints de gestion des frigos.
   - Les utilisateurs non administrateurs n'ont accès qu'au endpoint `/login`.

---

## Déploiement avec Docker

### Prérequis
- **Docker** doit être installé sur un système Linux, **WSL**, ou localement.

### Déploiement
1. Utilisez le fichier `docker-compose.yaml` pour déployer les conteneurs.
2. Les conteneurs incluent :
   - **L'API** elle-même.
   - **PostgreSQL** pour la base de données.

### Commandes principales
- Lancer les conteneurs :
  ```bash
  docker-compose up
  ```
- Arrêter les conteneurs :
  ```bash
  docker-compose down
  ```

---

## Documentation et test via Swagger
- **Swagger UI** permet :
   - Une documentation complète des endpoints.
   - Une interface interactive pour tester les fonctionnalités.
   - Une navigation simplifiée pour les développeurs.

---

## Technologies utilisées
- **Backend** : Java avec **Spring Boot**.
- **Base de données** : PostgreSQL.
- **Sécurité** :
   - **Spring Security** avec **JWT** pour l'authentification.
   - **BCrypt** pour le hachage des mots de passe.
- **Téléchargement de fichiers** : Gestion des fichiers XML pour importer des données.
- **Documentation** : **Swagger UI** pour documenter et tester l'API.
- **Conteneurisation** : **Docker** et **Docker Compose** pour le déploiement.

---

## Scénarios d'utilisation

### Connexion
- Un utilisateur se connecte via `/login` en fournissant son email et son mot de passe.
- Si les identifiants sont corrects, un token JWT est généré.

### Gestion des frigos
- Un administrateur peut ajouter, modifier ou supprimer des frigos en base.
- Les administrateurs peuvent également téléverser un fichier XML pour importer plusieurs frigos en une seule opération.

### Accès sécurisé
- Les utilisateurs non authentifiés ou non autorisés (sans le rôle Administrateur) ne peuvent pas accéder aux endpoints protégés.

---

Cette API offre une solution complète et sécurisée pour la gestion des frigos, avec une intégration Docker et une documentation Swagger facilitant le déploiement et l'utilisation.
