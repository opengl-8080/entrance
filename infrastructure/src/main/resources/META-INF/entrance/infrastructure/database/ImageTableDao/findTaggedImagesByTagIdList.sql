    SELECT ITEM.*
          ,IMAGE.PATH
      FROM ITEM
INNER JOIN IMAGE
        ON IMAGE.ITEM_ID = ITEM.ID
     WHERE
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
  ORDER BY ITEM.REGISTERED_DATETIME ASC
