package com.zaberp.zab.biwtabackend.repository.custom;

import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPoGrnHeaderRepository {
    Page<PogrnheaderXcusdto> getPogrnList(int zid, String user, String superior, String status, Pageable pageable);

}


