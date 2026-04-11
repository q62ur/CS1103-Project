# Pet Adoption Management System

**Name:** Chanchalpreet Kaur  
**Student ID:** 3772677
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

- Project Proposal (in `proposal/` folder): [Project Proposal](proposal/proposal.md)

- `code/` folder → Contains all Java source files and SQL scripts  
  - Includes JDBC implementation and application code

    - [DatabaseSetup.java](code/DatabaseSetup.java)
    - [PetAdoptionApp.java](code/PetAdoptionApp.java)
    - [PetAdoptionGUI.java](code/PetAdoptionGUI.java)
    - [schema.sql](code/schema.sql)

- Project Deliverables (in `report` folder):
  - Final Report: [Final Report](report/Final_Report.docx)
  - ER diagram: [ER Diagram](report/CS1103_Project_ER_Dia.drawio.png)  
  - Presentation slides: [PPT](report/Pet_Adoption_Presentation.pptx)   
  - User guide: [User Guide](report/README.md)

- Project Presentation Video (google drive link):
  
  [Watch Presentation Video](https://drive.google.com/file/d/13ITmAUxraNCqlUjHw1TVNFCx5gET_SSW/view?usp=sharing)

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
## Extra Exploration
A partial GUI version(PetAdoptionGUI.java) was started using Java Swing as an optional extension.
The main required implementation remains the console based system.
