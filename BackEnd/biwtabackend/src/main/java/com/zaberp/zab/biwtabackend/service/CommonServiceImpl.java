package com.zaberp.zab.biwtabackend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class CommonServiceImpl<T, ID> implements CommonService<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);


    //    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected abstract NamedParameterJdbcTemplate getJdbcTemplate();

    @Override
    public abstract JpaRepository<T, ID> getRepository();

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public CommonServiceImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<T> findByZidAndTransactionNumber(int zid, String transactionNumberColumn, String transactionNumber) {
        String sql = "SELECT * FROM " + getTableName() +
                " WHERE zid = :zid AND " + transactionNumberColumn + " = :transactionNumber";
        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber);

        return jdbcTemplate.query(sql, params, getRowMapper()).stream().findFirst();
    }

    @Override
    public Iterable<T> findByZid(int zid) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE zid = :zid";
        Map<String, Object> params = Map.of("zid", zid);
        return jdbcTemplate.query(sql, params, getRowMapper());
    }

    @Override
    public void deleteByZidAndTransactionNumber(int zid,String column,String transactionNumber) {
        String sql = "DELETE FROM " + getTableName() + " WHERE zid = :zid AND " + column + " = :transactionNumber";
        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Page<T> findByZidWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending) {
        // Construct the SQL query with a dynamic table name
        String sql = "SELECT * FROM " + getTableName() + " WHERE zid = :zid ORDER BY " + sortBy + (ascending ? " ASC" : " DESC");

        // Pagination using SQL LIMIT and OFFSET
        int offset = page * size;
        sql += " LIMIT :size OFFSET :offset";

        Map<String, Object> params = Map.of(
                "zid", zid,
                "size", size,
                "offset", offset
        );

        List<T> content = jdbcTemplate.query(sql, params, getRowMapper());

        String countSql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE zid = :zid";
        int totalCount = jdbcTemplate.queryForObject(countSql, Map.of("zid", zid), Integer.class);

        Pageable pageable = PageRequest.of(page, size, Sort.by(ascending ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));
        return new PageImpl<>(content, pageable, totalCount);
    }


    public List<T> getBySearchTextAndZid(int zid, String searchText, List<String> searchFields) {

        if (searchFields == null || searchFields.isEmpty()) {
            return Collections.emptyList();
        }
        // Dynamically construct the SQL query with search fields and zid
        String whereClause = searchFields.stream()
                .map(field -> field + " LIKE :searchText")
                .collect(Collectors.joining(" OR "));

        String sql = "SELECT * FROM " + getTableName() + " WHERE zid = :zid AND (" + whereClause + ")";

        Map<String, Object> params = Map.of(
                "zid", zid,
                "searchText", "%" + searchText + "%" // Use LIKE with wildcard for searching
        );
        try {
            System.out.println("in try");
            List<T> results = getJdbcTemplate().query(sql, params, getRowMapper());
            return results.isEmpty() ? Collections.emptyList() : results;
        } catch (Exception e) {
            // Log the exception
            System.out.println("in catch");
            logger.error("Error executing query: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    protected abstract String getTableName();

    protected abstract org.springframework.jdbc.core.RowMapper<T> getRowMapper();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

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
