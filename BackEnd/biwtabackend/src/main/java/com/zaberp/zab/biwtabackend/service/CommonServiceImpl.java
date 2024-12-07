package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.model.Caitem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CommonServiceImpl<T, ID> implements CommonService<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);


    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected abstract NamedParameterJdbcTemplate getJdbcTemplate();

    @Override
    public abstract JpaRepository<T, ID> getRepository();

    @Autowired
    private EntityManager entityManager;

    public CommonServiceImpl(){

    }

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
    public List<T> findRowsOfTransaction(int zid, String transactionNumberColumn, String transactionNumber) {
        String sql = "SELECT * FROM " + getTableName() +
                " WHERE zid = :zid AND " + transactionNumberColumn + " = :transactionNumber";

        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber);

        return jdbcTemplate.query(sql, params, getRowMapper());
    }

    public Page<T> findByZidAndTrnnumWithPaginationAndSorting(int zid,String trnnum, int page, int size, String sortBy, boolean ascending){
        return null;
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


//    public void deleteByConditions(int zid, Map<String, Object> additionalConditions) {
//        System.out.println("delete by conditions");
//        StringBuilder sql = new StringBuilder("DELETE FROM " + getTableName() + " WHERE zid = :zid");
//        Map<String, Object> params = Map.of("zid", zid);
//
//        // Append additional conditions if provided
//        if (additionalConditions != null && !additionalConditions.isEmpty()) {
//            additionalConditions.forEach((column, value) -> {
//                sql.append(" AND ").append(column).append(" = :").append(column);
//            });
//            params = new HashMap<>(params); // Ensure we can add more parameters
//            params.putAll(additionalConditions);
//        }
//
//        System.out.println(sql.toString());
//        jdbcTemplate.update(sql.toString(), params);
//    }


    @Override
    public void deleteByConditions(int zid,String column,String transactionNumber,int row) {
        String sql = "DELETE FROM " + getTableName() + " WHERE zid = :zid AND " + column + " = :transactionNumber and xrow = :row ";
        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber,"row",row);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Page<T> findByZidWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE zid = :zid ORDER BY " + sortBy + (ascending ? " ASC" : " DESC");

        int offset = page * size;
        sql += " OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY";

        Map<String, Object> params = Map.of(
                "zid", zid,
                "size", size,
                "offset", offset
        );
        List<T> content = jdbcTemplate.query(sql, params, getRowMapper());

        String countSql = "SELECT COUNT(1) FROM " + getTableName() + " WHERE zid = :zid";
        int totalCount = jdbcTemplate.queryForObject(countSql, Map.of("zid", zid), Integer.class);
        Pageable pageable = PageRequest.of(page, size, Sort.by(ascending ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));
        return new PageImpl<>(content, pageable, totalCount);
    }


    @Override
    public Page<T> findByZidAndOtherWithPaginationAndSorting(
            int zid,
            String columnName,
            String columnValue,
            int page,
            int size,
            String sortBy,
            boolean ascending
    ) {

        StringBuilder sql = new StringBuilder("SELECT * FROM " + getTableName() + " WHERE zid = :zid");
        if (columnName != null && columnValue != null) {
            sql.append(" AND ").append(columnName).append(" = :").append(columnName);
        }

        sql.append(" ORDER BY ").append(sortBy).append(ascending ? " ASC" : " DESC");
        int offset = page * size;
        sql.append(" OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY");

        Map<String, Object> params = new HashMap<>();
        params.put("zid", zid);
        if (columnName != null && columnValue != null) {
            params.put(columnName, columnValue);
        }
        params.put("size", size);
        params.put("offset", offset);

        List<T> content = jdbcTemplate.query(sql.toString(), params, getRowMapper());

        StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM " + getTableName() + " WHERE zid = :zid");
        if (columnName != null && columnValue != null) {
            countSql.append(" AND ").append(columnName).append(" = :").append(columnName);
        }

        int totalCount = jdbcTemplate.queryForObject(countSql.toString(), params, Integer.class);
        Pageable pageable = PageRequest.of(page, size, Sort.by(ascending ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));

        return new PageImpl<>(content, pageable, totalCount);
    }


    public List<T> getBySearchTextAndZid(int zid, String searchText, List<String> searchFields) {

        if (searchFields == null || searchFields.isEmpty()) {
            return Collections.emptyList();
        }

        String whereClause = searchFields.stream()
                .map(field -> field + " LIKE :searchText")
                .collect(Collectors.joining(" OR "));

        String sql = "SELECT * FROM " + getTableName() + " WHERE zid = :zid AND (" + whereClause + ")";

        Map<String, Object> params = Map.of(
                "zid", zid,
                "searchText", "%" + searchText + "%"
        );
        try {

            List<T> results = getJdbcTemplate().query(sql, params, getRowMapper());
            System.out.println(results);
            return results.isEmpty() ? Collections.emptyList() : results;
        } catch (Exception e) {
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
            if (value instanceof String) {
                String valueStr = (String) value;
                if (valueStr.matches("\\d{4}-\\d{2}-\\d{2}")) { // If matches yyyy-MM-dd format
                    query.setParameter(paramName, java.sql.Date.valueOf(valueStr));
                    return;
                }
            } else if (value instanceof java.time.LocalDate) {
                query.setParameter(paramName, java.sql.Date.valueOf((java.time.LocalDate) value));
                return;
            } else if (value instanceof java.util.Date) {
                query.setParameter(paramName, new java.sql.Date(((java.util.Date) value).getTime()));
                return;
            }

            query.setParameter(paramName, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }




}
