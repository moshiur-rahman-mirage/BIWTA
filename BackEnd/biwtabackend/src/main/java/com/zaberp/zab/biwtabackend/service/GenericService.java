package com.zaberp.zab.biwtabackend.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericService {


    int updateTableWithDynamicColumnsAndWhere(
            String tableName,
            Map<String, Object> updates,
            Map<String, Object> whereConditions
    );




}
