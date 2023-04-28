CREATE TABLE IF NOT EXISTS tour_category (
    tour_id SERIAL,
    category_id SERIAL,
    CONSTRAINT tour_category_pk PRIMARY KEY (tour_id, category_id),
    CONSTRAINT FK_tour FOREIGN KEY (tour_id) REFERENCES tours (tour_id),
    CONSTRAINT FK_category FOREIGN KEY (category_id) REFERENCES categories (category_id)
);