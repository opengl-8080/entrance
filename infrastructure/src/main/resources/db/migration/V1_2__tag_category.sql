CREATE TABLE TAG_CATEGORY (
    ID   BIGINT         NOT NULL AUTO_INCREMENT,
    NAME NVARCHAR2(100) NOT NULL,
    CONSTRAINT TAG_CATEGORY_PK PRIMARY KEY (ID),
    CONSTRAINT TAG_CATEGORY_UK1 UNIQUE (NAME)
);
INSERT INTO TAG_CATEGORY (NAME) VALUES ('その他');

ALTER TABLE TAG ADD COLUMN (TAG_CATEGORY_ID BIGINT NULL);
UPDATE TAG
   SET TAG_CATEGORY_ID = (SELECT ID FROM TAG_CATEGORY WHERE NAME = 'その他')
;
ALTER TABLE TAG ALTER COLUMN TAG_CATEGORY_ID SET NOT NULL;

ALTER TABLE TAG ADD CONSTRAINT TAG_FK1 FOREIGN KEY (TAG_CATEGORY_ID) REFERENCES TAG_CATEGORY (ID);