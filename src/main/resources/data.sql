--  werkt altijd van boven naar beneden.


INSERT INTO cars ( licenseplate, brand, mileage) VALUES ( '34-GBD-06', 'Toyota', 17004);
INSERT INTO cars (licenseplate, brand, mileage) VALUES ('AB-123-CD', 'Volkswagen', 205465);
INSERT INTO cars (licenseplate, brand, mileage) VALUES ('XY-987-ZZ', 'BMW', 11111);

-- INSERT INTO carparts (id, in_stock, car_part_enum)
-- VALUES (100, 12, 'TIRES');

ALTER TABLE carparts ADD COLUMN licenseplate VARCHAR(20);

INSERT INTO carparts (id, in_stock, car_part_enum, licenseplate)
VALUES (100, 12, 'TIRES', '34-GBD-06' );

-- Todo toevoegen repairs, invocie en inspections