package com.zaberp.zab.biwtabackend.repository;

import com.zaberp.zab.biwtabackend.dto.StockCheckDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockCheckRepository extends JpaRepository<StockCheckDto, String> {
    @Query(value = "SELECT * FROM stockcheckview " +
            "WHERE (:zid IS NULL OR zid = :zid) " +
            "AND (:xwh IS NULL OR xwh = :xwh) " +
            "AND (:xitem IS NULL OR xitem = :xitem) " +
            "AND (:xgitem IS NULL OR xgitem = :xgitem)",
            nativeQuery = true)
    List<StockCheckDto> findFilteredStock(
            @Param("zid") Long zid,
            @Param("xwh") String xwh,
            @Param("xitem") String xitem,
            @Param("xgitem") String xgitem);

}

