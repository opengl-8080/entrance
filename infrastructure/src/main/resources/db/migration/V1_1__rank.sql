ALTER TABLE ITEM ADD COLUMN (RANK TINYINT);

UPDATE ITEM SET RANK = 1;

ALTER TABLE ITEM ALTER COLUMN RANK SET NOT NULL;