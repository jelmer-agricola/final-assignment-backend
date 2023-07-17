--  werkt altijd van boven naar beneden.


INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ('34-GBD-06', 'Toyota', 17004, 'C.S. Jansen');
INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ('AB-123-CD', 'Toyota', 105465, 'P.J. Dijxhoorn');
INSERT INTO cars (licenseplate, brand, mileage, owner)
VALUES ('XY-987-ZZ', 'BMW', 11111, 'De boer');

-- met het aanmaken van de auto worden

--Car Parts Car  34-GBD-06
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (100, '34-GBD-06', 'BATTERIES', 'needs repair', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (101, '34-GBD-06', 'BRAKES', 'needs replacement', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (102, '34-GBD-06', 'TIRES', 'needs maintenance', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (103, '34-GBD-06', 'LIGHTS', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (104, '34-GBD-06', 'WINDSHIELD_WIPERS', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (105, '34-GBD-06', 'SUSPENSION', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (106, '34-GBD-06', 'ENGINE', 'in good condition', false);


--Car Parts Car AB-123-CD
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (200, 'AB-123-CD', 'BATTERIES', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (201, 'AB-123-CD', 'BRAKES', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (202, 'AB-123-CD', 'TIRES', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (203, 'AB-123-CD', 'LIGHTS', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (204, 'AB-123-CD', 'WINDSHIELD_WIPERS', 'needs replacement', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (205, 'AB-123-CD', 'SUSPENSION', 'needs replacement', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (206, 'AB-123-CD', 'ENGINE', 'in good condition', false);


--Car Parts Car XY-987-ZZ
INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (300, 'XY-987-ZZ', 'BATTERIES', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (301, 'XY-987-ZZ', 'BRAKES', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (302, 'XY-987-ZZ', 'TIRES', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (303, 'XY-987-ZZ', 'LIGHTS', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (304, 'XY-987-ZZ', 'WINDSHIELD_WIPERS', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (305, 'XY-987-ZZ', 'SUSPENSION', 'in good condition', false);

INSERT INTO carparts (id, car_licenseplate, car_part_enum, part_status, part_is_inspected)
VALUES (306, 'XY-987-ZZ', 'ENGINE', 'needs replacement', false);


INSERT INTO inspection (id, cost_estimate, inspection_description, inspection_approved, is_inspected, car_licenseplate)
VALUES (101, 100, 'Tires need ONDERHOUD', true, false, '34-GBD-06');


INSERT INTO inspection (id, cost_estimate, inspection_description, inspection_approved, is_inspected, car_licenseplate)
VALUES (102, 288, 'Inspection 2', true, false, 'AB-123-CD');



INSERT INTO repair (id, repair_description, part_repair_cost, repair_finished, inspection_id, carpart_id)
VALUES (1001, 'repair uno', 232, true, 101, 102);

INSERT INTO repair (id, repair_description, part_repair_cost, repair_finished, inspection_id, carpart_id)
VALUES (1002, 'repair duo', 232, true, 102, 202);
INSERT INTO repair (id, repair_description, part_repair_cost, repair_finished, inspection_id, carpart_id)
VALUES (1003, 'repair TRES', 232, true, 102, 201);


INSERT INTO invoice (id, final_cost, invoice, paid, car_licenseplate, inspection_id)
VALUES (101, 123, 1, true, '34-GBD-06', 101);



-- ALTER TABLE carparts
--     ADD COLUMN licenseplate VARCHAR(20);
--
-- INSERT INTO carparts (id, in_stock, car_part_enum, licenseplate)
-- VALUES (101, 12, 'TIRES', '34-GBD-06');

