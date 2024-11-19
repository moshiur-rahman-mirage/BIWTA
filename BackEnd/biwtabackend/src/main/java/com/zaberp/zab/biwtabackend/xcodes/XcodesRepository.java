package com.zaberp.zab.biwtabackend.xcodes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface XcodesRepository extends JpaRepository<Xcodes, XcodesId> , JpaSpecificationExecutor<Xcodes> {
}

