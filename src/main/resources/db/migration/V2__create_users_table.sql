CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    image_url TEXT,
    is_active BOOLEAN NOT NULL,
    born_date DATE NOT NULL,
    created_at TIMESTAMP
);
