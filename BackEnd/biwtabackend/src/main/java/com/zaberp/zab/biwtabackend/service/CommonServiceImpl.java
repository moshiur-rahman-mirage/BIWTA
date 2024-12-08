package com.zaberp.zab.biwtabackend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommonServiceImpl<T, ID> implements CommonService<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);


    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    @Override
    public abstract JpaRepository<T, ID> getRepository();

    @Autowired
    private EntityManager entityManager;



    public CommonServiceImpl(){
    }

    @Autowired
    public CommonServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<T> findByZidAndTransactionNumber(int zid, String transactionNumberColumn, String transactionNumber) {
        String sql = "SELECT * FROM " + getTableName() +
                " WHERE zid = :zid AND " + transactionNumberColumn + " = :transactionNumber";
        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber);

        return namedParameterJdbcTemplate.query(sql, params, getRowMapper()).stream().findFirst();
    }

    @Override
    public List<T> findRowsOfTransaction(int zid, String transactionNumberColumn, String transactionNumber) {
        String sql = "SELECT * FROM " + getTableName() +
                " WHERE zid = :zid AND " + transactionNumberColumn + " = :transactionNumber";

        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber);

        return namedParameterJdbcTemplate.query(sql, params, getRowMapper());
    }

    public Page<T> findByZidAndTrnnumWithPaginationAndSorting(int zid,String trnnum, int page, int size, String sortBy, boolean ascending){
        return null;
    }



    @Override
    public Iterable<T> findByZid(int zid) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE zid = :zid";
        Map<String, Object> params = Map.of("zid", zid);
        return namedParameterJdbcTemplate.query(sql, params, getRowMapper());
    }

    @Override
    public void deleteByZidAndTransactionNumber(int zid,String column,String transactionNumber) {
        String sql = "DELETE FROM " + getTableName() + " WHERE zid = :zid AND " + column + " = :transactionNumber";
        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber);
        namedParameterJdbcTemplate.update(sql, params);
    }




    @Override
    public void deleteByConditions(int zid,String column,String transactionNumber,int row) {
        String sql = "DELETE FROM " + getTableName() + " WHERE zid = :zid AND " + column + " = :transactionNumber and xrow = :row ";
        Map<String, Object> params = Map.of("zid", zid, "transactionNumber", transactionNumber,"row",row);
        namedParameterJdbcTemplate.update(sql, params);
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
        List<T> content = namedParameterJdbcTemplate.query(sql, params, getRowMapper());

        String countSql = "SELECT COUNT(1) FROM " + getTableName() + " WHERE zid = :zid";
        int totalCount = namedParameterJdbcTemplate.queryForObject(countSql, Map.of("zid", zid), Integer.class);
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

        List<T> content = namedParameterJdbcTemplate.query(sql.toString(), params, getRowMapper());

        StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM " + getTableName() + " WHERE zid = :zid");
        if (columnName != null && columnValue != null) {
            countSql.append(" AND ").append(columnName).append(" = :").append(columnName);
        }

        int totalCount = namedParameterJdbcTemplate.queryForObject(countSql.toString(), params, Integer.class);
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

            List<T> results = getNamedParameterJdbcTemplate().query(sql, params, getRowMapper());
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




    public String confirmRequest(int zid, String user, String position,String wh,String tornum,String request) {
        System.out.println("Executing procedure with params:");
        System.out.println("zid: " + zid + ", user: " + user + ", tornum: " + tornum + ", wh: " + wh+", position "+position );

        try {
            // Prepare the SQL query
            String sql = "EXEC zabsp_Confirmed_Request_SAS @zid = ?, @user = ?, @position=?,@wh=?,@tornum=?,@request=?";

            // Execute the query with parameter binding
            jdbcTemplate.update(sql, zid, user, position, wh, tornum,request);

            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }
    }


    public String approveRequest(int zid, String user, String position, String tornum, int ypd, String status, String aprcs) {
        System.out.println("here in service");

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("zabsp_apvprcs_SAS")
                    .declareParameters(
                            new SqlParameter("zid", Types.INTEGER),
                            new SqlParameter("user", Types.VARCHAR),
                            new SqlParameter("position", Types.VARCHAR),
                            new SqlParameter("reqnum", Types.VARCHAR),
                            new SqlParameter("ypd", Types.INTEGER),
                            new SqlParameter("status", Types.VARCHAR),
                            new SqlParameter("aprcs", Types.VARCHAR)
                    );

            SqlParameterSource inParams = new MapSqlParameterSource()
                    .addValue("zid", zid)
                    .addValue("user", user)
                    .addValue("position", position)
                    .addValue("reqnum", tornum)
                    .addValue("ypd", ypd)
                    .addValue("status", status)
                    .addValue("aprcs", aprcs);

            jdbcCall.execute(inParams);

            return "Procedure executed successfully!";
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }

    }




    @Transactional()
    public List<Object[]> getDynamicData(

            List<String> selectedFields,
            Map<String, Object> whereConditions) {


        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        if (selectedFields == null || selectedFields.isEmpty()) {
            queryBuilder.append("p.* ");
        } else {
            for (String field : selectedFields) {
                queryBuilder.append("p.").append(field).append(", ");
            }
            queryBuilder.setLength(queryBuilder.length() - 2);
        }

        queryBuilder.append(" FROM ").append(getTableName()).append(" p");

        if (whereConditions != null && !whereConditions.isEmpty()) {
            queryBuilder.append(" WHERE ");
            boolean isFirstCondition = true;
            for (String column : whereConditions.keySet()) {
                if (!isFirstCondition) {
                    queryBuilder.append(" AND ");
                }
                queryBuilder.append("p.").append(column).append(" = :").append(column);
                isFirstCondition = false;
            }
        }

        Query query = entityManager.createQuery(queryBuilder.toString());

        if (whereConditions != null) {
            for (Map.Entry<String, Object> entry : whereConditions.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        try {
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing dynamic query.", e);
        }
    }


    public List<Map<String, Object>> getDynamicDataWithColumnNames(
            List<String> selectedFields, Map<String, Object> whereConditions) {

        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        if (selectedFields == null || selectedFields.isEmpty()) {
            queryBuilder.append("*");
        } else {
            queryBuilder.append(String.join(", ", selectedFields));
        }

        queryBuilder.append(" FROM ").append(getTableName()).append(" WHERE 1=1");

        if (whereConditions != null) {
            whereConditions.forEach((key, value) -> queryBuilder.append(" AND ").append(key).append(" = :").append(key));
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), Tuple.class);

        if (whereConditions != null) {
            whereConditions.forEach(query::setParameter);
        }

        List<Tuple> tuples = query.getResultList();

        // Convert Tuple results to a list of maps
        return tuples.stream().map(tuple -> {
            Map<String, Object> resultMap = new HashMap<>();
            tuple.getElements().forEach(element -> resultMap.put(element.getAlias(), tuple.get(element.getAlias())));
            return resultMap;
        }).collect(Collectors.toList());
    }




    @Override
    public boolean isDataPresent(int zid, String transactionColumn, String transactionNumber) {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE zid = ? AND %s = ?", getTableName(), transactionColumn);
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, zid, transactionNumber);
        return count != null && count > 0;
    }



}
