# Coffee Clicker

Second-semester OOPD project at La Salle – URL. A Cookie Clicker-style idle game built in Java with Swing.

---

## What it is

You click a button to generate coffees. You spend coffees on generators that produce more coffees automatically. There is no end goal — just see how far you can go.

---

## Requirements

- Java JDK 17+
- MySQL running locally

---

## Setup

1. Clone the repo and open it in your IDE
2. Create a `config.json` in the root folder:

```json
{
  "db_port": 3306,
  "db_host": "localhost",
  "db_name": "coffee_clicker",
  "db_username": "root",
  "db_password": "yourpassword"
}
```

3. Run the SQL schema to create the tables
4. Run `Main.java`

---

## How to play

- Click the coffee button to generate coffees manually
- Buy generators from the shop to produce coffees automatically
- Buy upgrades to multiply generator output
- The game saves when you exit and picks up where you left off

---

## Project structure

Follows a three-layer architecture: Model, View, Presentation.

## Authors

Albert Baiget, Stefan Radulescu, Daiki Fabius Kudo, Berta Margenats, Paula Vivancos
La Salle – URL, May 2026