ALTER TABLE IMAGE ADD COLUMN (WIDTH INT NULL);
ALTER TABLE IMAGE ADD COLUMN (HEIGHT INT NULL);

UPDATE IMAGE SET WIDTH=-1, HEIGHT=-1;

ALTER TABLE IMAGE ALTER COLUMN WIDTH SET NOT NULL;
ALTER TABLE IMAGE ALTER COLUMN HEIGHT SET NOT NULL;