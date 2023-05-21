ALTER TABLE daybooks ADD COLUMN is_deleted BOOLEAN DEFAULT false;
ALTER TABLE time_book_details ADD COLUMN is_deleted BOOLEAN DEFAULT false;