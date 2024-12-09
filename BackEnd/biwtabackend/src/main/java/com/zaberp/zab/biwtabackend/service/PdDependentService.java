package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.PdDependentId;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.repository.PdDependentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PdDependentService extends CommonServiceImpl<PdDependent, PdDependentId> {

    @Autowired
    private PdDependentRepository repository;

    @Override
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public PdDependentRepository getRepository() {

        return repository;
    }


    @Override
    protected String getTableName() {
        return "PdDependent";
    }

    @Override
    protected RowMapper<PdDependent> getRowMapper() {
        return new BeanPropertyRowMapper<>(PdDependent.class);
    }



    public Integer getMaxXrowByZidAndXstaff(int zid, String xstaff) {
        Integer maxXrow = repository.findMaxXrowByZidAndXstaff(zid, xstaff);
        return (maxXrow == null) ? 0 : maxXrow;
    }


}
