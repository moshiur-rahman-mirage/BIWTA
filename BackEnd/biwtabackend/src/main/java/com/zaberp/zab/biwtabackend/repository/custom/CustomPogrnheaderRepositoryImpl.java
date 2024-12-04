package com.zaberp.zab.biwtabackend.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;




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
    public class CustomPogrnheaderRepositoryImpl implements CustomPogrnheaderRepository {

        @Autowired
        private EntityManager entityManager;

        @Override
        public int updateExcludingColumns(int zid, String xgrnnum, Map<String, Object> updates, List<String> excludeColumns) {
            StringBuilder queryBuilder = new StringBuilder("UPDATE Pogrnheader p SET ");
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


            if (isFirst) {
                throw new IllegalArgumentException("No valid columns to update.");
            }

            queryBuilder.append(" WHERE p.zid = :zid AND p.xgrnnum = :xgrnnum");

            Query query = entityManager.createQuery(queryBuilder.toString());
            query.setParameter("zid", zid);
            query.setParameter("xgrnnum", xgrnnum);

            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                if (!excludeColumns.contains(entry.getKey())) {
                    Object value = entry.getValue();


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

