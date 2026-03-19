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
