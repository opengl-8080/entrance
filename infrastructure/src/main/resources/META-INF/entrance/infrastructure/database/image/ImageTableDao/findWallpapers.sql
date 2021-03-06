    SELECT ITEM.*
          ,IMAGE.PATH
          ,IMAGE.WIDTH
          ,IMAGE.HEIGHT
      FROM ITEM
INNER JOIN IMAGE
        ON IMAGE.ITEM_ID = ITEM.ID
     WHERE ITEM.RANK BETWEEN /*minRank*/3 AND /*maxRank*/5
  ORDER BY RAND() ASC
     LIMIT 30
