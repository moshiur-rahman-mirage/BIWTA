package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.dto.ImtordetailDto;
import com.zaberp.zab.biwtabackend.id.ImtordetailId;
import com.zaberp.zab.biwtabackend.model.Imtordetail;
import com.zaberp.zab.biwtabackend.repository.ImtordetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ImtordetailService extends CommonServiceImpl<Imtordetail,ImtordetailId>{

    @Autowired
    private ImtordetailRepository repository;

    @Override
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public ImtordetailRepository getRepository() {
        return repository;
    }

    @Override
    protected String getTableName() {
        return "Imtordetail";
    }

    @Override
    protected RowMapper<Imtordetail> getRowMapper() {
        return new BeanPropertyRowMapper<>(Imtordetail.class);
    }

    public Imtordetail save(Imtordetail detail) {
        System.out.println("imtordetail");
        Integer maxXrow = repository.findMaxXrowByZidAndXtornum(detail.getZid(), detail.getXtornum());
        if (maxXrow == null) {
            maxXrow = 0;
        }

        detail.setXrow(maxXrow + 1);
        System.out.println(maxXrow);
        System.out.println(detail.getZid());
        System.out.println(detail.getXtornum());
        detail.setXdphqty(detail.getXprepqty());
        detail.setXqtyreq(detail.getXprepqty());
        detail.setXqtyord(detail.getXprepqty());
        return repository.save(detail);
    }


    public Page<ImtordetailDto> getImtordetail(int zid, String xtornum, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByZid(zid,xtornum, pageable);
    }


}


