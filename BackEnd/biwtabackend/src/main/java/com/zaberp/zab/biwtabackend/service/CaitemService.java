package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.repository.CaitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaitemService extends CommonServiceImpl<Caitem, CaitemId> {
    private final CaitemRepository caitemRepository;
    private final PrimaryKeyService primaryKeyService;
    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    public CaitemService(CaitemRepository caitemRepository,
                         PrimaryKeyService primaryKeyService,
                         NamedParameterJdbcTemplate jdbcTemplate) {
        this.caitemRepository = caitemRepository;
        this.primaryKeyService = primaryKeyService;
        this.jdbcTemplate = jdbcTemplate;
    }




    public Caitem createItem(Caitem caitem) {
        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(
                caitem.getZid(), "Item Code", "IC--", 6);
        caitem.setXitem(generatedKey.substring(4));
        caitem.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        caitem.setZtime(LocalDateTime.now());
        return caitemRepository.save(caitem);
    }

    public List<Caitem> searchByText(int zid, String searchText) {
        return caitemRepository.findBySearchTextAndZid(zid,searchText);
    }

    @Override
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {

        return jdbcTemplate;
    }

    @Override
    public JpaRepository<Caitem, CaitemId> getRepository() {
        return caitemRepository;
    }

    @Override
    protected String getTableName() {
        return "caitem"; // Replace with your actual table name if necessary.
    }

    @Override
    protected RowMapper<Caitem> getRowMapper() {
        return new BeanPropertyRowMapper<>(Caitem.class);
    }
}
