/* Populate tables */
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('Andrés', 'Guzman', 'profesor@bolsadeideas.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe2@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe3@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe4@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe5@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe6@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe7@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe8@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe9@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe10@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe11@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe12@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe13@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe14@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe15@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe16@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe17@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe18@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe19@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe20@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe21@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe22@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe23@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe24@gmail.com', DATE(NOW()), '1982-02-15');
INSERT INTO clientes (nombre, apellido, email, create_at, dob) values ('John', 'Doe', 'john.doe25@gmail.com', DATE(NOW()), '1982-02-15');

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES ('Panasonic Pantalla LCD', 2599.90, DATE(NOW()));
INSERT INTO productos (nombre, precio, create_at) VALUES ('Apple IPOD Shuffle', 14999.90, DATE(NOW()));
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Notebook Z110', 379.90, DATE(NOW()));
INSERT INTO productos (nombre, precio, create_at) VALUES ('Hewlett Packard Multifuncional F2280', 699.90, DATE(NOW()));
INSERT INTO productos (nombre, precio, create_at) VALUES ('Bianchi Bicicleta aro 26', 699.90, DATE(NOW()));
INSERT INTO productos (nombre, precio, create_at) VALUES ('Mica comoda 5 cajones', 2999.90, DATE(NOW()));
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Camara Digital DSC-W320B', 1234.90, DATE(NOW()));

/* Facturas de ejemplo */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura equipos de oficina', null, 1, DATE(NOW()));
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura Bicicleta', 'Alguna Nota Importante!', 1, DATE(NOW()));
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (3, 2, 6);

/* Usuarios y Roles de ejemplo */
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$SldkhrcrGQJXq6lDUtdK/utiJarfA2YG5p73qAz7Ebj22dfJzoIme', true);
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$TzxN.PJBG3rswVy6Sf32QO21jfxc1ZP03Yg2Wjgx6RtvTXIOmHzvG', true);


INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
