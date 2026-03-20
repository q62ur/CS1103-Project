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

--Table for adoption applications submitted by adopters
CREATE TABLE AdoptionApplication
(
    --unique ID for each application
    application_id INT PRIMARY KEY,

    --which adopter submitted the application
    adopter_id INT,

    --which pet is being applied for
    pet_id INT,

    --date of application
    application_date DATE,

    --status of application (Pending, Approved, Rejected)
    status VARCHAR(20),

    --link to adopter
    FOREIGN KEY (adopter_id) REFERENCES Adopter(adopter_id),

    --link to pet
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)
);

--Table for completed pet adoptions
CREATE TABLE AdoptionRecord
(
    --unique ID for each adoption record
    record_id INT PRIMARY KEY,

    --adopter who adopted the pet
    adopter_id INT,

    --pet that got adopted
    pet_id INT,

    --date of adoption
    adoption_date DATE,

    --link to adopter
    FOREIGN KEY (adopter_id) REFERENCES Adopter(adopter_id),

    --link to pet
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)
);

--------------------------------------------------------------
--Sample shelter records
INSERT INTO Shelter VALUES
(1, 'Happy Tails Shelter', 'Saint John'),
(2, 'Safe Paws Shelter', 'Fredericton');

--Sample pet records
INSERT INTO Pet VALUES
(1, 'Max', 'Dog', 'Labrador', 3, 'Available', 1),
(2, 'Bella', 'Cat', 'Siamese', 2, 'Available', 1),
(3, 'Charlie', 'Dog', 'Beagle', 4, 'Adopted', 2);

--Sample adopter records
INSERT INTO Adopter VALUES
(1, 'John Smith', '1234567890', 'john@example.com', 'Saint John'),
(2, 'Emma Brown', '9876543210', 'emma@example.com', 'Fredericton');

--Sample staff records
INSERT INTO Staff VALUES
(1, 'Alice Green', 'Manager', 1),
(2, 'Bob White', 'Volunteer', 2);

--Sample adoption application records
INSERT INTO AdoptionApplication VALUES
(1, 1, 1, '2026-03-15', 'Pending'),
(2, 2, 3, '2026-03-16', 'Approved');

--Sample adoption records
INSERT INTO AdoptionRecord VALUES
(1, 2, 3, '2026-03-18');
