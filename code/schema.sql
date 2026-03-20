--Table for shelters where pets are kept
CREATE TABLE Shelter 
(
    --unique ID for each shelter
    shelter_id INT PRIMARY KEY,
    --shelter name
    name VARCHAR(100) NOT NULL,
    --shelter location
    location VARCHAR(100) NOT NULL
);

--Table for pets available for adoption
CREATE TABLE Pet 
(
    --unique ID for each pet
    pet_id INT PRIMARY KEY,
    --pet name
    name VARCHAR(100) NOT NULL,
    --dog, cat, etc.
    species VARCHAR(50) NOT NULL,
    --breed of pet
    breed VARCHAR(50),
    --age of pet
    age INT,
    --Available / Adopted
    adoption_status VARCHAR(20) NOT NULL,
    --which shelter the pet belongs to
    shelter_id INT,
    FOREIGN KEY (shelter_id) REFERENCES Shelter(shelter_id)
);

--Table for people who want to adopt pets
CREATE TABLE Adopter
(
    --unique ID for each adopter
    adopter_id INT PRIMARY KEY,

    --adopter name
    name VARCHAR(100) NOT NULL,

    --contact number
    phone VARCHAR(20),

    --email address
    email VARCHAR(100),

    --home address
    address VARCHAR(200)
);

--Table for staff working at shelters
CREATE TABLE Staff
(
    --unique ID for each staff member
    staff_id INT PRIMARY KEY,

    --staff name
    name VARCHAR(100) NOT NULL,

    --job role (manager, volunteer, etc.)
    role VARCHAR(50),

    --which shelter the staff works at
    shelter_id INT,

    --link staff to shelter
    FOREIGN KEY (shelter_id) REFERENCES Shelter(shelter_id)
);
