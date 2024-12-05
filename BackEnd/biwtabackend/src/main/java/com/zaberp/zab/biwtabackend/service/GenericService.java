package com.zaberp.zab.biwtabackend.service;

import java.util.Map;

public interface GenericService {

    int updateTableWithDynamicColumnsAndWhere(
            String tableName,
            Map<String, Object> updates,
            Map<String, Object> whereConditions
    );

}
