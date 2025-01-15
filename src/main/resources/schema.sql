CREATE TABLE IF NOT EXISTS tbl_customer (
                                            id SERIAL PRIMARY KEY,
                                            name VARCHAR(255) NOT NULL,
                                            email VARCHAR(255) UNIQUE NOT NULL,
                                            address VARCHAR(255),
                                            password VARCHAR(255) NOT NULL
);
