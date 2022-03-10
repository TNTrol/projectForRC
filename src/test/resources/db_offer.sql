
CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    description VARCHAR(30) NOT NULL,
    name VARCHAR(30) UNIQUE NOT NULL,
    type VARCHAR(30) NOT NULL,
    cost DECIMAL NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(30) UNIQUE NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    address VARCHAR(100),
    name VARCHAR(30)
);

CREATE TABLE offer (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    cost DECIMAL NOT NULL,
    status VARCHAR(30) NOT NULL,
    date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE stock (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    percent INTEGER NOT NULL,
    count INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE pack_product (
    id SERIAL PRIMARY KEY,
    product_id INTEGER,
    offer_id INTEGER NOT NULL,
    count INTEGER NOT NULL,
    FOREIGN KEY(product_id) REFERENCES product(id) ON DELETE SET NULL,
    FOREIGN KEY(offer_id) REFERENCES offer(id)
);

CREATE TABLE user_to_role (
    id SERIAL PRIMARY KEY,
    role_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO users(id, login, email, password, address) VALUES(1, 'admin', '!admin@mail.com', '$2a$10$pjks52MIIKhny3jqm88fHOzc5NkrhF2SvbR.s2Z/p96Kp/QuPCAuC', 'dffj lfg');
INSERT INTO product(id, description, name, type, cost) VALUES(1, 'test_d1', 'test_n1', 'CONTROLLER', 100.1), (2, 'test_d2', 'test_n2', 'CONTROLLER', 150.0), (3, 'test_d3', 'test_n3', 'SENSOR', 250.0);