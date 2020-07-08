
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS suppliers CASCADE;


CREATE TABLE categories
(
    id          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(255),
    department  VARCHAR(255),
    description TEXT
);

CREATE TABLE suppliers
(
    id          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(255),
    description TEXT
);

CREATE TABLE products
(
    id          SERIAL PRIMARY KEY NOT NULL,
    supplier_id INTEGER REFERENCES suppliers (id) ON DELETE CASCADE,
    category_id INTEGER REFERENCES categories (id) ON DELETE CASCADE,
    name        VARCHAR(255),
    description TEXT,
    image       VARCHAR(255),
    price       FLOAT,
    currency    VARCHAR(10)
);


INSERT INTO suppliers (name, description)
VALUES ('Amazon', 'Digital content and services.');
INSERT INTO suppliers (name, description)
VALUES ('HP', 'Computers and electronics.');


INSERT INTO categories (name, department, description)
VALUES ('Tablets', 'Hardware',
        'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO categories (name, department, description)
VALUES ('Notebook', 'Hardware', 'A notebook computer is a battery- or AC-powered personal computer generally smaller than a briefcase that can easily be transported and conveniently used in temporary spaces such as on airplanes, in libraries, temporary offices, and at meetings..');


INSERT INTO products (supplier_id, category_id, name, description, image, price, currency)
VALUES (1, 1, 'Amazon Fire','Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 'product_1.jpg', 49.9, 'USD');

INSERT INTO products (supplier_id, category_id, name, description, image, price, currency)
VALUES (1, 1, 'Amazon Fire HD 8','Latest Fire HD 8 tablet is a great value for media consumption.', 'product_3.jpg', 89, 'USD');

INSERT INTO products (supplier_id, category_id, name, description, image, price, currency)
VALUES (1, 1, 'Lenovo IdeaPad Miix 700','Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports.', 'picture_2.jpg', 479, 'USD');

INSERT INTO products (supplier_id, category_id, name, description, image, price, currency)
VALUES (2, 2,'HP Pavilion','HP Laptop. Great value at a greater price", notebook, amazon.', 'picture_4.jpg', 700, 'USD');



