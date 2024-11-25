package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.repository.PrimaryKeyRepository;
import org.springframework.stereotype.Service;

@Service
public class PrimaryKeyService {

    private final PrimaryKeyRepository primaryKeyRepository;

    public PrimaryKeyService(PrimaryKeyRepository primaryKeyRepository) {
        this.primaryKeyRepository = primaryKeyRepository;
    }

    public String getGeneratedPrimaryKey(int zid, String xtypetrn, String xtrn,int lenV) {
        return primaryKeyRepository.generatePrimaryKey(zid, xtypetrn, xtrn,lenV);
    }
}
