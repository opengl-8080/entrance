    SELECT ITEM.*
          ,IMAGE.PATH
      FROM ITEM
INNER JOIN IMAGE
        ON IMAGE.ITEM_ID = ITEM.ID
     WHERE ITEM.RANK BETWEEN /*minRank*/3 AND /*maxRank*/5
     /*%for tagId : tagIdList*/
     AND EXISTS (
           SELECT 1
             FROM ITEM_TAG
       INNER JOIN TAG
               ON TAG.ID = ITEM_TAG.TAG_ID
            WHERE ITEM_TAG.ITEM_ID = ITEM.ID
              AND TAG.ID = /* tagId */1
     )
     /*%end*/
  ORDER BY ITEM.RANK DESC
          ,ITEM.REGISTERED_DATETIME ASC
