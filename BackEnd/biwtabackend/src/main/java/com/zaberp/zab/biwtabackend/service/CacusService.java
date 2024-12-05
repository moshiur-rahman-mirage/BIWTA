package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.repository.CacusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CacusService extends CommonServiceImpl<Cacus, CacusId> {

    private final CacusRepository cacusRepository;

    @Autowired
    public CacusService(NamedParameterJdbcTemplate jdbcTemplate, CacusRepository cacusRepository) {
        super(jdbcTemplate);
        this.cacusRepository = cacusRepository;
    }

    @Override
    protected NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public JpaRepository<Cacus, CacusId> getRepository() {
        return cacusRepository;
    }

    @Override
    protected String getTableName() {
        return "Cacus";
    }

    @Override
    protected RowMapper<Cacus> getRowMapper() {
        return new BeanPropertyRowMapper<>(Cacus.class);
    }

    public Optional<Cacus> getCacusById(CacusId id) {
        return cacusRepository.findById(id);
    }

    @Override
    public Cacus save(Cacus entity) {
        return cacusRepository.save(entity);
    }

    public List<Cacus> getCacusByZidAndXtype(int zid, String xtype) {
        return cacusRepository.findByZidAndXtype(zid, xtype);
    }

    public List<Cacus> searchByZidAndText(int zid, String searchText) {
        return getBySearchTextAndZid(zid, searchText, List.of("xcus", "xorg", "xmadd"));
    }

    public Page<Cacus> getItemsWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending) {
        return findByZidWithPaginationAndSorting(zid, page, size, sortBy, ascending);
    }


}
