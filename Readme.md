# 🌙 MIRA API – Muslim Information Resources and Assistance
MIRA API est une solution développée par **H Technologies**, conçue pour fournir des ressources et une assistance aux musulmans, incluant le suivi des mosquées, les notifications et bien plus encore.
HForge	"Nous forgeons vos idées numériques"
---

## 📌 État du Projet
✅ **Version actuelle** : 1.0.0  
✅ **Hébergement** : Render  
✅ **Base de données** : PostgreSQL  
✅ **Statut** : En développement (99% complété)

---

## ⚙️ Prérequis et Installation

### 📌 Technologies utilisées
- Java (Spring Boot)
- PostgreSQL
- JWT pour l’authentification
- Docker

### 📥 Installation locale
1. **Cloner le projet**
   ```bash
   git clone https://github.com/ton-repo/mira-api.git
   cd mira-api
   ```

2. **Configurer l’environnement**  
   Crée un fichier `.env` avec les variables nécessaires :
   ```env
   DATABASE_URL=jdbc:postgresql://localhost:5432/miradb
   
   ```

3. **Lancer l’API**
   ```bash
   mvn spring-boot:run
   ```

---

## 🚀 Démarrage rapide
### Tester une requête avec `cURL`
```bash
curl -X POST http://localhost:8080/muslimApi/v1/authentication/authenticate      -H "Content-Type: application/json"      -d '{ "login": "admin",  "password": "test"}'
```
Réponse attendue :
```json
{
   "accessToken": "Token_key",
   "nom": null,
   "prenom": null,
   "email": "admin",
   "photo": null
}
```

---

## 🔐 Authentification et Sécurité
- **Connexion** : Email + Mot de passe default credential (admin+test)
- **Authentification** : JWT Token requis
- **Sécurité** : Pas besoin de Bearer lors de la connexion avec Swagger

Exemple d'en-tête pour une requête sécurisée :
```json
Authorization: Bearer <your_jwt_token>
```

---

## 🔗 Endpoints de l’API
### 🌍 Endpoints publics (non sécurisés)
| Méthode | Endpoint | Description |
|---------|---------|-------------|
| `POST`  | `/muslimApi/v1/authentication/authenticate` | Authentification utilisateur |
| `POST`  | `/logout` | Déconnexion de l’utilisateur |

### 🔒 Endpoints sécurisés (JWT requis)
| Méthode | Endpoint | Description |
|---------|----|-------------|
| `GET`  | `/mosque/find/all/` | Récupérer la liste des mosquées |
| `POST`  | `/mosque/nouveau/{update}` | enregistrer une mosquee|
| `DELETE`  | `/mosques/delete  ` | Se désabonner d’une mosquée |

---

## ⚠️ Gestion des erreurs
| Code | Signification | Explication |
|------|-------------|-------------|
| `400` | Requête invalide | Paramètres manquants ou incorrects |
| `401` | Non autorisé | JWT Token manquant ou invalide |
| `403` | Accès refusé | Permission insuffisante |
| `404` | Ressource non trouvée | Endpoint ou ressource inexistante |
| `500` | Erreur serveur | Problème interne à l’API |

---

## 📂 Fonctionnalités à implémenter
- 📌 **DocumentController** : Téléchargement et streaming live des fichiers audio/vidéo
- 📌 **FileController** : Importation et exportation de données

---

## 🚀 Déploiement
### Avec Docker
```bash
docker-compose up -d
```
### Hébergement sur Render
1. Pousser le code sur GitHub
2. Connecter Render à GitHub et déployer

---

## 🤝 Contributions
Les contributions sont les bienvenues ! Pour contribuer :
1. Forker le projet
2. Créer une branche (`feature-nouvelle-fonctionnalité`)
3. Soumettre une Pull Request

---

## 📜 Licence
MIT License – Vous êtes libre de modifier et utiliser ce projet tant que vous mentionnez l’auteur original.

---

## 📬 Contact et Support
📧 Email :kalifakalih12@gmail.com  
📌 Documentation complète : [Lien vers Swagger/Postman](#)

---
## Developed by H Technologies