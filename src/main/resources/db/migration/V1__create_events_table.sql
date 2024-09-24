CREATE TABLE events (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    category TEXT NOT NULL,
    country TEXT NOT NULL,
    state TEXT NOT NULL,
    city TEXT NOT NULL,
    address TEXT NOT NULL,
    image_url TEXT,
    redirect_url TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP
);
