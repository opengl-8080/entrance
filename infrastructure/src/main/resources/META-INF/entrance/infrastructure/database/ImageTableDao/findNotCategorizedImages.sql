    SELECT ITEM.*
          ,IMAGE.PATH
      FROM ITEM
INNER JOIN IMAGE
        ON IMAGE.ITEM_ID = ITEM.ID
     WHERE ITEM.IS_NOT_CATEGORIZED = TRUE
  ORDER BY ITEM.REGISTERED_DATETIME ASC
