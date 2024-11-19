package com.zaberp.zab.biwtabackend.xusers;


import com.zaberp.zab.biwtabackend.xcodes.XcodesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface XusersRepository extends JpaRepository<Xusers, XusersId>, JpaSpecificationExecutor<Xusers> {

    boolean existsByZidAndZemailAndXposition(Integer zid, String zemail, String xposition);
    boolean existsByZidAndZemail(Integer zid, String zemail);
}
