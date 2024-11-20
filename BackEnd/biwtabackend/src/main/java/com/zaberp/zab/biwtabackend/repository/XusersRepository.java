package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.XusersId;
import com.zaberp.zab.biwtabackend.model.Xusers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface XusersRepository extends JpaRepository<Xusers, XusersId>, JpaSpecificationExecutor<Xusers> {

    boolean existsByZidAndZemailAndXposition(Integer zid, String zemail, String xposition);
    boolean existsByZidAndZemail(Integer zid, String zemail);

    Xusers findByZemail(String zemail);

}
