    SELECT ITEM.*
          ,BOOK.PATH
          ,BOOK.NAME
      FROM ITEM
INNER JOIN BOOK
        ON BOOK.ITEM_ID = ITEM.ID
     WHERE NOT EXISTS (
           SELECT 1
             FROM ITEM_TAG
            WHERE ITEM_TAG.ITEM_ID = ITEM.ID
     )
       AND ITEM.RANK BETWEEN /*minRank*/3 AND /*maxRank*/5
  ORDER BY ITEM.RANK DESC
          ,ITEM.REGISTERED_DATETIME ASC