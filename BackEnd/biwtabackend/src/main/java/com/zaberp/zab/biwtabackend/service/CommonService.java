package com.zaberp.zab.biwtabackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface CommonService<T,Id> {

    Optional<T> findByZidAndTransactionNumber(int zid,String column, String transactionNumber);
    Iterable<T> findByZid(int zid);

    JpaRepository<T, Id> getRepository();

    public void deleteByZidAndTransactionNumber(int zid,String column,String transactionNumber);

    public Page<T> findByZidWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending);

    public List<T> getBySearchTextAndZid(int zid, String searchText, List<String> searchFields);

    T save(T entity);

    int updateTableWithDynamicColumnsAndWhere(
            String tableName,
            Map<String, Object> updates,
            Map<String, Object> whereConditions
    );


}
