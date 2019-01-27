INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$SldkhrcrGQJXq6lDUtdK/utiJarfA2YG5p73qAz7Ebj22dfJzoIme', 1);
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$TzxN.PJBG3rswVy6Sf32QO21jfxc1ZP03Yg2Wjgx6RtvTXIOmHzvG', 1);


INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
