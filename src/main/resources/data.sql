--  werkt altijd van boven naar beneden.


INSERT INTO cars (licenseplate, brand, mileage, owner) VALUES ('34-GBD-06', 'Toyota', 17004, 'C.S. Jansen');
INSERT INTO cars (licenseplate, brand, mileage, owner) VALUES ('AB-123-CD', 'Toyota', 105465, 'P.J. Dijxhoorn');
INSERT INTO cars (licenseplate, brand, mileage, owner) VALUES ('XY-987-ZZ', 'BMW', 11111, 'De boer');

-- met het aanmaken van de auto worden

--Car Parts Car  34-GBD-06
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status ) VALUES (100, '34-GBD-06', 'BATTERIES', 'needs repair');
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status) VALUES (101, '34-GBD-06', 'BRAKES', 'needs replacement');
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status) VALUES (102, '34-GBD-06', 'TIRES', 'needs repair');
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status ) VALUES (103, '34-GBD-06', 'LIGHTS', 'needs repair');
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status) VALUES (104, '34-GBD-06', 'WINDSHIELD_WIPERS', 'needs repair');
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status ) VALUES (105, '34-GBD-06', 'SUSPENSION', 'needs repair');

--Car Parts Car AB-123-CD
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (200, 'AB-123-CD', 'BATTERIES');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (201, 'AB-123-CD', 'BRAKES');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (202, 'AB-123-CD', 'TIRES');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (203, 'AB-123-CD', 'LIGHTS');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (204, 'AB-123-CD', 'WINDSHIELD_WIPERS');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (205, 'AB-123-CD', 'SUSPENSION');

--Car Parts Car XY-987-ZZ
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (300, 'XY-987-ZZ', 'BATTERIES');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (301, 'XY-987-ZZ', 'BRAKES');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (302, 'XY-987-ZZ', 'TIRES');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (303, 'XY-987-ZZ', 'LIGHTS');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (304, 'XY-987-ZZ', 'WINDSHIELD_WIPERS');
INSERT INTO carparts (id, car_licenseplate, car_part_enum) VALUES (305, 'XY-987-ZZ', 'SUSPENSION');


INSERT INTO inspection (id, cost_estimate, description, repair_approved)
VALUES (1001, 100, 'Inspection 1', true);
INSERT INTO inspection (id, cost_estimate, description, repair_approved)
VALUES (1002, 288, 'Inspection 2', true);

INSERT INTO repair (id, description, cost, repair_finished)
VALUES (1001, 'repair uno', 232, true);

INSERT INTO invoice (id, repair_cost, invoice, paid)
VALUES (101, 123, 1, true);




-- ALTER TABLE carparts
--     ADD COLUMN licenseplate VARCHAR(20);
--
-- INSERT INTO carparts (id, in_stock, car_part_enum, licenseplate)
-- VALUES (101, 12, 'TIRES', '34-GBD-06');

