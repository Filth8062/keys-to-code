# KeysToCode ⌨️ → 💻

> **FR** · Apprendre la dactylographie *et* la programmation en construisant soi-même son propre entraîneur de frappe, de la console jusqu'à une application web en ligne.
> **EN** · Learning touch typing *and* programming by building my own typing trainer, from a console program to an online web application.

<!-- TODO (exercise 5+): add the CI badge here. TODO (exercise 6): add the coverage badge. -->

---

## 🇫🇷 Le projet

J'ai démarré ce projet sans jamais avoir écrit une ligne de code. Le principe :

1. **Je m'entraîne à taper** avec l'exercice en cours, jusqu'à atteindre l'objectif de vitesse et de précision.
2. **Je programme l'exercice suivant** : chaque exercice ajoute une nouvelle fonctionnalité à l'application *et* m'apprend une nouvelle notion d'informatique.
3. Je recommence, jusqu'à **40 mots par minute à 95 % de précision**… et jusqu'à une application web déployée sur un vrai serveur.

### Le parcours

| Étape | Application | Ce que j'apprends |
|---|---|---|
| Exercices 1 à 7 | Console | Java, typage, conditions, boucles, classes, tests JUnit, Git, fichiers, CI |
| Exercices 8 à 10 | Application de bureau (JavaFX) | Interfaces graphiques, MVC, événements, packaging, releases |
| Exercices 11 à 17 | Application web (Spring Boot) | Linux, réseau, HTTP, API REST, base de données, sécurité, Docker, HTTPS, déploiement |

### Architecture

L'application est découpée en **3 couches** dès le premier jour :

```
presentation  →  ce que voit l'utilisatrice (console, puis fenêtre, puis page web)
business      →  les règles de l'exercice (réutilisées telles quelles partout)
data          →  où sont rangées les données (fichiers, puis base de données)
```

Passer de la console au bureau puis au web ne demande de remplacer **que** la couche `presentation`.

### Lancer le projet

Prérequis : JDK 25 et Maven (ou simplement IntelliJ IDEA, qui fournit les deux).

```bash
mvn verify      # compile et lance tous les tests
mvn exec:java   # lance l'entraîneur de frappe dans la console
```

### Mon journal d'apprentissage

Ce que j'ai appris, mes difficultés et mes solutions, exercice par exercice : [docs/journal](docs/journal/README.md).

---

## 🇬🇧 The project

I started this project without ever having written a line of code. Each exercise is both a **typing lesson** and a **programming milestone**: I practise until I reach the speed and accuracy target, then I code the next feature of the trainer myself.

- **Exercises 1–7**: console application: Java basics, strong typing, OOP, unit tests (JUnit), Git, file I/O, continuous integration.
- **Exercises 8–10**: desktop application (JavaFX): GUI, MVC, events, packaging, releases.
- **Exercises 11–17**: web application (Spring Boot): Linux, networking, HTTP, REST API, database, security, Docker, HTTPS, deployment.

Three-layer architecture (presentation / business / data) from day one: moving from console to desktop to web only replaces the presentation layer.

```bash
mvn verify      # build and run all tests
mvn exec:java   # start the console typing trainer
```

---

## Credits

- **Project idea, learning path and course material:** my mentor. The initial skeleton (exercise 1) and the course were
  produced by my mentor with the help of an AI assistant (Claude, Anthropic), then reviewed and adapted by him.
- **Everything from exercise 2 onwards:** written by me.
- **Use of AI:** I used AI tools as a tutor (explanations, hints, code review), never as a ghost-writer, following the
  rules in [`AGENTS.md`](AGENTS.md). Commits where AI helped significantly end with an `AI-assisted:` trailer.

> **FR** · L'idée du projet, le parcours et le cours viennent de mon mentor, qui a utilisé une IA pour les rédiger plus
> vite. Tout le code à partir de l'exercice 2 est de moi ; j'ai utilisé l'IA comme tuteur et je l'indique dans mes
> commits (`AI-assisted:`).

License: [MIT](LICENSE)
