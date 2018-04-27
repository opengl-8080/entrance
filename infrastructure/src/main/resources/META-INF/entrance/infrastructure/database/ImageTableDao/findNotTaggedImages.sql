    SELECT ITEM.*
          ,IMAGE.PATH
      FROM ITEM
INNER JOIN IMAGE
        ON IMAGE.ITEM_ID = ITEM.ID
     WHERE NOT EXISTS (
           SELECT 1
             FROM ITEM_TAG
            WHERE ITEM_TAG.ITEM_ID = ITEM.ID
     )
  ORDER BY ITEM.RANK DESC
          ,ITEM.REGISTERED_DATETIME ASC