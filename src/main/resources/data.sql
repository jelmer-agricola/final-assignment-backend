--  werkt altijd van boven naar beneden.


INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ('34-GBD-06', 'Toyota', 17004, 'C.S. Jansen');
INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ('AB-123-CD', 'Toyota', 105465, 'P.J. Dijxhoorn');
INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ('XY-987-ZZ', 'BMW', 11111, 'De boer');


--- Car Parts Car 34-GBD-06
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (100, '34-GBD-06', 'BATTERIES', 'needs repair', false, 45);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (101, '34-GBD-06', 'BRAKES', 'needs replacement', false, 456);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (102, '34-GBD-06', 'TIRES', 'needs maintenance', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (103, '34-GBD-06', 'LIGHTS', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (104, '34-GBD-06', 'WINDSHIELD_WIPERS', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (105, '34-GBD-06', 'SUSPENSION', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (106, '34-GBD-06', 'ENGINE', 'in good condition', false);

-- Car Parts Car AB-123-CD
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (200, 'AB-123-CD', 'BATTERIES', 'in good condition', false, 11);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (201, 'AB-123-CD', 'BRAKES', 'in good condition', false, 123);

-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (202, 'AB-123-CD', 'TIRES', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (203, 'AB-123-CD', 'LIGHTS', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (204, 'AB-123-CD', 'WINDSHIELD_WIPERS', 'needs replacement', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (205, 'AB-123-CD', 'SUSPENSION', 'needs replacement', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (206, 'AB-123-CD', 'ENGINE', 'in good condition', false);

-- Car Parts Car XY-987-ZZ
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (300, 'XY-987-ZZ', 'BATTERIES', 'in good condition', false, 111);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (301, 'XY-987-ZZ', 'BRAKES', 'in good condition', false, 12);

-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (302, 'XY-987-ZZ', 'TIRES', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (303, 'XY-987-ZZ', 'LIGHTS', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (304, 'XY-987-ZZ', 'WINDSHIELD_WIPERS', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (305, 'XY-987-ZZ', 'SUSPENSION', 'in good condition', false);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
-- VALUES (306, 'XY-987-ZZ', 'ENGINE', 'needs replacement', false);


--  USERS EN AUTHORITIES TOEVEOGEN


INSERT INTO inspection (id, inspection_description, inspection_finished, car_licenseplate,
                        client_approved)
VALUES (101, 'Tires need ONDERHOUD', false, '34-GBD-06', false);



INSERT INTO inspection (id, inspection_description, inspection_finished, car_licenseplate,
                        client_approved)
VALUES (102, 'Inspection 2', false, 'AB-123-CD', false);



INSERT INTO repair (id, repair_description, repair_finished, inspection_id, car_part_id)
VALUES (101, 'repair uno', true, 101, 101);

INSERT INTO repair (id, repair_description, repair_finished, inspection_id, car_part_id)
VALUES (102, 'repair duo', true, 102, 201);
INSERT INTO repair (id, repair_description, repair_finished, inspection_id, car_part_id)
VALUES (103, 'repair TRES', true, 102, 201);



-- HIER STAAT EEN AUTO KLAAR WAARMEE JE METEEN EEN INVOICE AAN KAN MAKEN.
INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ('TTBB3', 'Toyota', 50000, 'Jan Akkerman');

INSERT INTO inspection (id, inspection_description, inspection_finished, car_licenseplate,
                        client_approved)
VALUES (1001, 'Inspection 2', true, 'TTBB3', true);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (1001, 'TTBB3', 'BATTERIES', 'in good condition', true, 111);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
VALUES (1002, 'TTBB3', 'BRAKES', 'in good condition', true, 12);

-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
-- VALUES (1003, 'TTBB3', 'TIRES', 'in good condition', false, NULL);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
-- VALUES (1004, 'TTBB3', 'LIGHTS', 'in good condition', false, NULL);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
-- VALUES (1005, 'TTBB3', 'WINDSHIELD_WIPERS', 'in good condition', false, NULL);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
-- VALUES (1006, 'TTBB3', 'SUSPENSION', 'in good condition', false, NULL);
--
-- INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected, car_part_cost)
-- VALUES (1007, 'TTBB3', 'ENGINE', 'needs replacement', false, NULL);


INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
VALUES (1001, 'repair uno', true, 1001);
INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
VALUES (1002, 'repair uno', true, 1001);
-- INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
-- VALUES (1003, 'repair uno', true, 1001);
-- INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
-- VALUES (1004, 'repair uno', true, 1001);
-- INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
-- VALUES (1005, 'repair uno', true, 1001);
-- INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
-- VALUES (1006, 'repair uno', true, 1001);
-- INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
-- VALUES (1007 'repair uno', true, 1001);

UPDATE repair
SET car_part_id = '1001'
WHERE id = 1001;
UPDATE repair
SET car_part_id = '1002'
WHERE id = 1002;
-- UPDATE repair
-- SET car_part_id = '1003'
-- WHERE id = 1003;
-- UPDATE repair
-- SET car_part_id = '1004'
-- WHERE id = 1004;
-- UPDATE repair
-- SET car_part_id = '1005'
-- WHERE id = 1005;
-- UPDATE repair
-- SET car_part_id = '1006'
-- WHERE id = 1006;
-- UPDATE repair
-- SET car_part_id = '1007'
-- WHERE id = 1007;
-- UPDATE repair


-- Normale Admin
INSERT INTO users (username, password, email, enabled, firstname, lastname)
VALUES ('Admin', '$2a$12$gyjVnhz7ZJj1.tI75ShFHeKw4nqRNDnUO6hwVN0/3fFcLv01OdmuS', 'admin@okaysjon.nl', TRUE, 'Jelmer', 'admin1'),
       ('ExtraAdmin', '$2a$12$Fq4dkYV7jFtt1K9KWvqr/eaqP3v4p3KLKtKJ4Yuzwge9hKHs/Tem6', 'adminextra@okaysjon.nl', TRUE,
        'Jelmer', 'Admin');
-- 123
INSERT INTO authorities (username, authority)
VALUES ('ExtraAdmin', 'ROLE_ADMIN'),
       ('Admin', 'ROLE_ADMIN');


-- Admin met toegang tot alles
INSERT INTO users (username, password, email, enabled)
VALUES ('SuperAdmin', '$2a$12$Rcb4UkMGtLOF3cYjCgYJ7eVrMOLTeay.UWvK7yCwBqPOuxrLiis0m', 'superadmin@okaysjon.com', TRUE);
-- 1234
INSERT INTO authorities (username, authority)
VALUES ('SuperAdmin', 'ROLE_MECHANIC');
INSERT INTO authorities (username, authority)
VALUES ('SuperAdmin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority)
VALUES ('SuperAdmin', 'ROLE_OFFICE');

-- Mechanics
INSERT INTO users (username, password, email, enabled, firstname, lastname)
VALUES ('Mechanic1', '$2a$12$Fq4dkYV7jFtt1K9KWvqr/eaqP3v4p3KLKtKJ4Yuzwge9hKHs/Tem6', 'monteur1@okaysjon.nl', TRUE,
        'Hans', 'Anders'),
       ('Mechanic2', '$2a$12$Fq4dkYV7jFtt1K9KWvqr/eaqP3v4p3KLKtKJ4Yuzwge9hKHs/Tem6', 'monteur2@okaysjon.nl', TRUE,
        'Sjaak', 'Afhaak'),
       ('Mechanic3', '$2a$12$Fq4dkYV7jFtt1K9KWvqr/eaqP3v4p3KLKtKJ4Yuzwge9hKHs/Tem6', 'monteur3@okaysjon.nl', TRUE,
        'Tina', 'Turner'),
       ('Mechanic4', '$2a$12$Fq4dkYV7jFtt1K9KWvqr/eaqP3v4p3KLKtKJ4Yuzwge9hKHs/Tem6', 'monteur4@okaysjon.nl', TRUE,
        'Nico', 'Dijxhoorn');
-- 123
INSERT INTO authorities (username, authority)
VALUES
       ('Mechanic1', 'ROLE_MECHANIC'),
       ('Mechanic2', 'ROLE_MECHANIC'),
       ('Mechanic3', 'ROLE_MECHANIC'),
       ('Mechanic4', 'ROLE_MECHANIC');
-- Office medewerkers
INSERT INTO users (username, password, email, enabled, firstname, lastname)
VALUES ('Office1', '$2a$12$BH.SrycalF8famQq0KR84O40RAqxzSjM.7ZIMG36AqqZF6rsPnp0a', 'administratie1@okaysjon.nl', TRUE,
        'Henk', 'Amster'),
       ('Office2', '$2a$12$BH.SrycalF8famQq0KR84O40RAqxzSjM.7ZIMG36AqqZF6rsPnp0a', 'administratie2@okaysjon.nl', TRUE,
        'Berend', 'Botje'),
       ('Office3', '$2a$12$BH.SrycalF8famQq0KR84O40RAqxzSjM.7ZIMG36AqqZF6rsPnp0a', 'administratie3@okaysjon.nl', TRUE,
        'Kees', 'van Amstel'),
       ('Office4', '$2a$12$BH.SrycalF8famQq0KR84O40RAqxzSjM.7ZIMG36AqqZF6rsPnp0a', 'administratie4@okaysjon.nl', TRUE,
        'Sjaan', 'Banaan');
-- 123
INSERT INTO authorities (username, authority)
VALUES ('Office1', 'ROLE_OFFICE'),
       ('Office2', 'ROLE_OFFICE'),
       ('Office3', 'ROLE_OFFICE'),
       ('Office4', 'ROLE_OFFICE');

