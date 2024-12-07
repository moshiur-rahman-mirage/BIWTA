package com.zaberp.zab.biwtabackend.repository;




import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.id.XusersId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.model.Xusers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PdmstRepository extends JpaRepository<Pdmst, PdmstId>, JpaSpecificationExecutor<Pdmst> {

    @Query("SELECT e FROM Pdmst e WHERE e.zid = :zid and  e.xstaff LIKE %:searchText% OR e.xname LIKE %:searchText% OR" +
            " e.xmobile LIKE %:searchText% OR e.xjobtitle LIKE %:searchText%")
    List<Pdmst> findBySearchTextAndZid(@Param("zid") String zid, @Param("searchText") String searchText);

    boolean existsByZidAndXstaffAndXposition(Integer zid, String xstaff, String xposition);

    boolean existsByZidAndXposition(Integer zid,String xposition);


    boolean existsByZidAndXmobile(Integer zid, String xmobile);

    List <Pdmst> findByZidAndZactive(int zid,String zactive);

    Pdmst findByZidAndXposition(int zid, String xposition);
}
