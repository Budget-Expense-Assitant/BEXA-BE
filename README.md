# BEXA-BE

Dies ist das Backend-Projekt **BEXA-BE**, entwickelt mit **Spring Boot** und **MongoDB**.  
Das Backend stellt REST-APIs bereit und übernimmt die Geschäftslogik sowie die Datenpersistenz.

---

## Technologien

- **Java** (Spring Boot)
- **Maven** (Build- & Dependency-Management)
- **MongoDB** (NoSQL-Datenbank)
- **Spring Web** (REST API)
- **Spring Data MongoDB** (Datenzugriff)
- **MongoDB Compass** (empfohlenes GUI-Tool)

---

## Voraussetzungen

- Java JDK 25
- Maven 3.9
- MongoDB (lokal oder remote)
- Optional: IDE (IntelliJ IDEA empfohlen)

---
## Projektstart

### 1. Repository klonen:

```bash
git clone <REPO_URL>
cd BEXA-BE
```
### 2. MongoDB starten:
#### Lokal (Standard):
```bash
mongod

```
---
## Konfiguration

### Die Konfiguration erfolgt über die Datei:

```
src/main/resources/application.properties

Beispiel:

spring.data.mongodb.uri=mongodb://localhost:27017/bexa
server.port=8080
```