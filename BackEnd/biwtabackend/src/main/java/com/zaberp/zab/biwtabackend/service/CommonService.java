package com.zaberp.zab.biwtabackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface CommonService<T,Id> {

    Optional<T> findByZidAndTransactionNumber(int zid,String column, String transactionNumber);
    Iterable<T> findByZid(int zid);
    public List<T> findRowsOfTransaction(int zid, String transactionNumberColumn, String transactionNumber);
    JpaRepository<T, Id> getRepository();
    public void deleteByZidAndTransactionNumber(int zid,String column,String transactionNumber);
    public void deleteByConditions(int zid, Map<String, Object> additionalConditions);
    public Page<T> findByZidWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending);
    public Page<T> findByZidAndTrnnumWithPaginationAndSorting(int zid,String trnnum, int page, int size, String sortBy, boolean ascending);
    public Page<T> findByZidAndOtherWithPaginationAndSorting(int zid,String columnName,String columnValue, int page, int size, String sortBy, boolean ascending);
    public List<T> getBySearchTextAndZid(int zid, String searchText, List<String> searchFields);
    T save(T entity);
    int updateTableWithDynamicColumnsAndWhere(
            String tableName,
            Map<String, Object> updates,
            Map<String, Object> whereConditions
    );


}
