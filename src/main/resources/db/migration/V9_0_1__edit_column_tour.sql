ALTER TABLE tours ALTER COLUMN latitude TYPE DOUBLE PRECISION USING latitude::double precision;
ALTER TABLE tours ALTER COLUMN longitude TYPE DOUBLE PRECISION USING longitude::double precision;