package com.zaberp.zab.biwtabackend.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;

@Repository
public class CustomImtorheaderRepositoryImpl implements CustomImtorheaderRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public int updateExcludingColumns(int zid, String xtornum, Map<String, Object> updates, List<String> excludeColumns) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE Imtorheader p SET ");
        boolean isFirst = true;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            if (!excludeColumns.contains(entry.getKey())) {
                if (!isFirst) {
                    queryBuilder.append(", ");
                }
                queryBuilder.append("p.").append(entry.getKey()).append(" = :").append(entry.getKey());
                isFirst = false;
            }
        }

        // Validate if at least one column is being updated
        if (isFirst) {
            throw new IllegalArgumentException("No valid columns to update.");
        }

        queryBuilder.append(" WHERE p.zid = :zid AND p.xtornum = :xtornum");

        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setParameter("zid", zid);
        query.setParameter("xtornum", xtornum);

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            if (!excludeColumns.contains(entry.getKey())) {
                Object value = entry.getValue();

                // Check if the value is a string and represents a date or datetime
                if (value instanceof String) {
                    String stringValue = (String) value;
                    try {
                        LocalDate date = LocalDate.parse(stringValue, dateFormatter);
                        query.setParameter(entry.getKey(), date);
                    } catch (Exception e1) {
                        try {
                            LocalDateTime dateTime = LocalDateTime.parse(stringValue, dateTimeFormatter);
                            query.setParameter(entry.getKey(), dateTime);
                        } catch (Exception e2) {
                            query.setParameter(entry.getKey(), stringValue);
                        }
                    }
                } else {
                    query.setParameter(entry.getKey(), value);
                }
            }
        }

        return query.executeUpdate();
    }
}
