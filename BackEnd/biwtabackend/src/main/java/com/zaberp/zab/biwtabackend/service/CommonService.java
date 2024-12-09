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
    public String approveRequest(int zid, String user, String position, String tornum, int ypd, String status, String aprcs);
    public void deleteByConditions(int zid,String column,String transactionNumber,int row);
    public void deleteByZidAndTransactionNumber(int zid,String column,String transactionNumber);
    public String confirmRequest(int zid, String user, String position,String wh,String tornum,String request);
    public Page<T> findByZidWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending);
    public Page<T> findByZidAndTrnnumWithPaginationAndSorting(int zid,String trnnum, int page, int size, String sortBy, boolean ascending);
    public Page<T> findByZidAndOtherWithPaginationAndSorting(int zid,String columnName,String columnValue, int page, int size, String sortBy, boolean ascending);
    public List<T> getBySearchTextAndZid(int zid, String searchText, List<String> searchFields);
    public List<Object[]> getDynamicData(List<String> selectedFields,Map<String, Object> whereConditions);
    public List<Map<String, Object>> getDynamicDataWithColumnNames(
            List<String> selectedFields, Map<String, Object> whereConditions);
    T save(T entity);
    int updateTableWithDynamicColumnsAndWhere(
            String tableName,
            Map<String, Object> updates,
            Map<String, Object> whereConditions
    );



    public boolean isDataPresent(int zid, String transactionColumn, String transactionNumber);


}
