    SELECT ITEM.*
          ,IMAGE.PATH
      FROM ITEM
INNER JOIN ITEM_TAG
        ON ITEM_TAG.ITEM_ID = ITEM.ID
INNER JOIN TAG
        ON TAG.ID = ITEM_TAG.TAG_ID
INNER JOIN IMAGE
        ON IMAGE.ITEM_ID = ITEM.ID
     WHERE 
       /*%for tagId : tagIdList*/
       AND TAG.ID = /* tagId */1
       /*%end*/
  ORDER BY ITEM.REGISTERED_DATETIME ASC
