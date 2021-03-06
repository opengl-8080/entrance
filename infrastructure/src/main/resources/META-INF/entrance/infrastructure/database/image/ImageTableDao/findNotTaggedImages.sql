    SELECT ITEM.*
          ,IMAGE.PATH
          ,IMAGE.WIDTH
          ,IMAGE.HEIGHT
      FROM ITEM
INNER JOIN IMAGE
        ON IMAGE.ITEM_ID = ITEM.ID
     WHERE NOT EXISTS (
           SELECT 1
             FROM ITEM_TAG
            WHERE ITEM_TAG.ITEM_ID = ITEM.ID
     )
       AND ITEM.RANK BETWEEN /*minRank*/3 AND /*maxRank*/5
  ORDER BY ITEM.RANK DESC
          ,ITEM.REGISTERED_DATETIME ASC