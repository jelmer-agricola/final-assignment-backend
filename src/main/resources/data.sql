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
VALUES (101,  'Tires need ONDERHOUD', false, '34-GBD-06', false);



INSERT INTO inspection (id,  inspection_description, inspection_finished, car_licenseplate,
                        client_approved)
VALUES (102,  'Inspection 2', false, 'AB-123-CD', false);



INSERT INTO repair (id, repair_description, repair_finished, inspection_id, car_part_id)
VALUES (101, 'repair uno', true, 101, 101);

INSERT INTO repair (id, repair_description, repair_finished, inspection_id, car_part_id)
VALUES (102, 'repair duo', true, 102, 201);
INSERT INTO repair (id, repair_description, repair_finished, inspection_id, car_part_id)
VALUES (103, 'repair TRES', true, 102, 201);


-- HIER STAAT EEN AUTO KLAAR WAARMEE JE METEEN EEN INVOICE AAN KAN MAKEN.
INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ( 'TTBB3', 'Toyota', 50000, 'Jan Akkerman');

INSERT INTO inspection (id, inspection_description, inspection_finished, car_licenseplate,
                        client_approved)
VALUES (1001,  'Inspection 2', true, 'TTBB3', true);

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
-- VALUES (1008, 'TTBB3', 'ENGINE', 'needs replacement', false, NULL);



INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
VALUES (1001, 'repair uno', true, 1001);
INSERT INTO repair (id, repair_description, repair_finished, inspection_id)
VALUES (1002, 'repair uno', true, 1001);

UPDATE repair SET car_part_id = '1001' WHERE id = 1001;
UPDATE repair SET car_part_id = '1002' WHERE id = 1002;
