package com.zaberp.zab.biwtabackend.serviceimpl;

import com.zaberp.zab.biwtabackend.service.GenericService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenericServiceImpl implements GenericService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public int updateTableWithDynamicColumnsAndWhere(
            String tableName,
            Map<String, Object> updates,
            Map<String, Object> whereConditions
    ) {

        if (updates == null || updates.isEmpty()) {
            throw new IllegalArgumentException("Updates cannot be null or empty.");
        }

        StringBuilder queryBuilder = new StringBuilder("UPDATE " + tableName + " p SET ");

        // Build the SET clause
        boolean isFirstUpdate = true;
        for (String column : updates.keySet()) {
            if (!isFirstUpdate) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("p.").append(column).append(" = :").append(column);
            isFirstUpdate = false;
        }

        if (isFirstUpdate) {
            throw new IllegalArgumentException("No valid columns to update.");
        }

        // Build the WHERE clause
        queryBuilder.append(" WHERE ");
        boolean isFirstCondition = true;
        for (String column : whereConditions.keySet()) {
            if (!isFirstCondition) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append("p.").append(column).append(" = :").append(column);
            isFirstCondition = false;
        }


        Query query = entityManager.createQuery(queryBuilder.toString());


        for (Map.Entry<String, Object> entry : updates.entrySet()) {

            try {
                setParameter(query, entry.getKey(), entry.getValue());
            } catch (Exception e) {

                e.printStackTrace();
            }
        }


        for (Map.Entry<String, Object> entry : whereConditions.entrySet()) {

            try {
                setParameter(query, entry.getKey(), entry.getValue());
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        try {

            int result = query.executeUpdate();

            return result;
        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }
    }

    private void setParameter(Query query, String paramName, Object value) {
        try {

            query.setParameter(paramName, value);
        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }
    }
}
