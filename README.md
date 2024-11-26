# 🍋 Citronix
**Application de gestion de ferme de citrons**

## 📝 Contexte du Projet
Citronix est une solution innovante conçue pour aider les fermiers à gérer efficacement leurs fermes de citrons. Cette application permet un suivi complet de la production, de la récolte et de la vente des produits, tout en optimisant la gestion des ressources et en tenant compte de la productivité des arbres selon leur âge.

---

## 🚀 Fonctionnalités Principales

### 🏠 Gestion des Fermes
- Créer, modifier, supprimer et consulter les informations d'une ferme :
    - Nom, localisation, superficie, date de création.
- Recherche multicritère grâce à **Criteria Builder** pour une gestion simplifiée.

### 🌾 Gestion des Champs
- Associer des champs à une ferme tout en respectant les contraintes suivantes :
    - **Superficie minimale :** 0,1 hectare (1 000 m²).
    - **Superficie maximale :** 50 % de la superficie totale de la ferme.
    - **Nombre maximal :** 10 champs par ferme.

### 🌳 Gestion des Arbres
- Suivi précis des arbres :
    - Date de plantation, âge, champ d'appartenance.
    - Calcul automatique de l’âge des arbres.
- Productivité annuelle calculée en fonction de l’âge :
    - **Jeune (< 3 ans) :** 2,5 kg/saison.
    - **Mature (3-10 ans) :** 12 kg/saison.
    - **Vieux (> 10 ans) :** 20 kg/saison.
- Gestion de la durée de vie des arbres (max. 20 ans).

### 🌦️ Gestion des Récoltes
- Suivi des récoltes par saison (**hiver, printemps, été, automne**).
- Enregistrement des dates et de la quantité totale récoltée.
- Une seule récolte par saison par champ.

### 📋 Détail des Récoltes
- Association des récoltes aux arbres spécifiques.
- Suivi précis des quantités récoltées par arbre.

### 💰 Gestion des Ventes
- Suivi des ventes avec :
    - Date, prix unitaire, client, récolte associée.
- Calcul automatique des revenus :
    - **Revenu = quantité × prix unitaire**.

---

## ⚙️ Contraintes Fonctionnelles
- **Superficie des champs :**
    - Minimale : 0,1 ha.
    - Maximale : 50 % de la superficie totale de la ferme.
- **Espacement entre les arbres :** max. 100 arbres/ha (10 arbres pour 1 000 m²).
- **Durée de vie maximale des arbres :** 20 ans.
- **Période de plantation :** mars à mai uniquement.
- **Limites saisonnières :**
    - Une seule récolte par saison pour chaque champ.
    - Un arbre ne peut participer qu'à une seule récolte par saison.

---

## 🛠️ Exigences Techniques
- **Framework :** Spring Boot pour l’API REST.
- **Architecture :** Architecture en couches :
    - Controller, Service, Repository, Entity.
- **Validation des données :** Annotations Spring.
- **Outils supplémentaires :**
    - MapStruct pour la conversion entités ⇄ DTO ⇄ View Models.
    - Lombok et le Builder Pattern pour simplifier les entités.
- **Gestion des exceptions :** Centralisée.
- **Tests :** JUnit et Mockito pour les tests unitaires.

---

## 📂 Structure du Projet

```bash
Citronix/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── projet/
│   │   │   │       └── citronix/
│   │   │   │           ├── controllers/        # Gestion des requêtes REST
│   │   │   │           ├── services/           # Logique métier
│   │   │   │           ├── repositories/        # Accès aux données
│   │   │   │           ├── models/              # Modèles de données
│   │   │   │           ├── dtos/                # Objets de transfert de données
│   │   │   │           ├── mapper/              # Mappers pour la conversion entre DTO et entités
│   │   │   │           └── exceptions/           # Classes d'exception personnalisées
│   │   ├── resources/
│   │   │   └── application.properties            # Configuration de la base de données
│   └── test/                  # Tests unitaires
└── README.md
````
---
## 🌱 Comment Contribuer ?

### Cloner le projet :

```bash
git clone https://github.com/asmaabarj/citronix.git
```
### Configurer l’environnement :
1. Installer **Java** et **Maven**.
2. Configurer la base de données dans `application.properties`.

### Lancer l’application :

```bash
mvn spring-boot:run
```
---
## 📋 Présentation 
https://www.canva.com/design/DAGXkKYBgnk/sgHYxvdcHsrakx0uj42iag/edit

---

## 📅 Planification sur Jira avec gitFlow
https://asmaabarj5.atlassian.net/jira/software/projects/CIT/boards/298

---

Merci d'utiliser **Citronix** !
