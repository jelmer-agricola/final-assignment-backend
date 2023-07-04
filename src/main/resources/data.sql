--  werkt altijd van boven naar beneden.


INSERT INTO cars ( licenseplate, brand, mileage) VALUES ( '34-GBD-06', 'Toyota', 17004);
INSERT INTO cars (licenseplate, brand, mileage) VALUES ('AB-123-CD', 'Volkswagen', 205465);
INSERT INTO cars (licenseplate, brand, mileage) VALUES ('XY-987-ZZ', 'BMW', 11111);

INSERT INTO inspection (id, cost_estimate, description, repair_approved)
VALUES (1001, 100, 'Inspection 1', true);
INSERT INTO inspection (id, cost_estimate, description, repair_approved)
VALUES (1002, 288, 'Inspection 2', true);

INSERT INTO repair (id, description, cost, repair_finished)
VALUES (1001, 'repair uno', 232, true);

-- INSERT INTO invoice  (id, repairCost, invoice, paid) VALUES (101, 1000, 1, true);


-- INSERT INTO carparts (id, in_stock, car_part_enum)
-- VALUES (100, 12, 'TIRES');

ALTER TABLE carparts ADD COLUMN licenseplate VARCHAR(20);

INSERT INTO carparts (id, in_stock, car_part_enum, licenseplate)
VALUES (100, 12, 'TIRES', '34-GBD-06' );

-- Todo toevoegen owners aan cars en invoice fixen