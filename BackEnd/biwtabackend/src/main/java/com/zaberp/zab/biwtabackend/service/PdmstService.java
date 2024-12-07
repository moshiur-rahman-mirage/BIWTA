package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.repository.PdmstRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PdmstService extends CommonServiceImpl<Pdmst, PdmstId> {

    private final PdmstRepository repository;
    private final PrimaryKeyService primaryKeyService;

    public PdmstService(PdmstRepository repository, PrimaryKeyService primaryKeyService) {
        this.repository = repository;
        this.primaryKeyService = primaryKeyService;
    }

    @Override
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public PdmstRepository getRepository() {
        return repository;
    }

    @Override
    protected String getTableName() {
        return "pdmst";
    }

    @Override
    protected RowMapper<Pdmst> getRowMapper() {
        return new BeanPropertyRowMapper<>(Pdmst.class);
    }

    public Pdmst createPdmst(Pdmst pdmst) {
        if (existsByZidAndXmobile(pdmst.getZid(), pdmst.getXmobile())) {
            throw new IllegalArgumentException("Validation failed: A Employee with the same mobile number already exists.");
        }
        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(pdmst.getZid(), "Staff ID", "EID-", 5);
        pdmst.setXstaff("1" + generatedKey.substring(Math.max(0, generatedKey.length() - 5)));
        pdmst.setXposition(pdmst.getXstaff());
        pdmst.setZtime(LocalDateTime.now());
        pdmst.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());

        return save(pdmst);
    }



    public boolean existsByZidAndXmobile(Integer zid, String xmobile) {
        return repository.existsByZidAndXmobile(zid, xmobile);
    }

    public List<Pdmst> findByZidAndZactive(int zid, String zactive) {
        return repository.findByZidAndZactive(zid, zactive);
    }



    public Pdmst findByZidAndXposition(int zid, String xposition) {
        return repository.findByZidAndXposition(zid, xposition);
    }
}
