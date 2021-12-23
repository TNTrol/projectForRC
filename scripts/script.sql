
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
    password VARCHAR(30) NOT NULL,
    name VARCHAR(30)
);

CREATE TABLE offer (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    cost DECIMAL NOT NULL,
    status VARCHAR(30) NOT NULL,
    date TIMESTAMP NOT NULL;
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE stock (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    percent INTEGER NOT NULL,
    count INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE offer_to_product (
    product_id INTEGER,
    offer_id INTEGER NOT NULL,
    count INTEGER NOT NULL,
    FOREIGN KEY(product_id) REFERENCES product(id) ON DELETE SET NULL,
    FOREIGN KEY(offer_id) REFERENCES offer(id),
    PRIMARY KEY(product_id, offer_id)
);

CREATE TABLE user_to_role (
    id SERIAL PRIMARY KEY,
    role_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);




