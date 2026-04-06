# Pet Adoption Management System

**Student:** Chanchalpreet Kaur  
**Course:** CS1103  
**Instructor:** Jong-Kyou Kim  

## Project Description
This project implements a Pet Adoption Management System database.  
The system manages information about pets available for adoption, adopters, shelters and adoption applications.

A basic graphical interface using Java Swing was also explored as an optional extension(see PetAdoptionGUI.java).

The database will allow users to:
- view pets available for adoption
- submit adoption applications
- track adoption status
- manage shelter and staff information

## Repository Structure
- `proposal/` → project proposal  
- `code/` → SQL scripts and Java JDBC code  
- `report/` → ER diagram,presentation; and final report/presentation

## Setup Instructions

Download SQLite JDBC driver from:
https://github.com/xerial/sqlite-jdbc/releases

Place the .jar file in the project folder before running the program.

## How to Run

1. Run DatabaseSetup.java to create database
2. Run PetAdoptionApp.java to use the system
3. (Optional) Run PetAdoptionGUI.java to explore the graphical interface(partial implementation)

## Technologies Used
- Java
- JDBC
- SQLite
- SQL
- Java Swing (GUI exploration)

## ER Diagram
See report/er_diagram.png
- SQLite
- SQL

## Extra Exploration
A partial GUI version(PetAdoptionGUI.java) was started using Java Swing as an optional extension.
The main required implementation remains the console based system.
