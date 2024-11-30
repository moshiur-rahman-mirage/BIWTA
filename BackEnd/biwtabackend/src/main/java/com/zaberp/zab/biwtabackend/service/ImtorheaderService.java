package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.repository.ImtorheaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImtorheaderService {

    @Autowired
    private ImtorheaderRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final PrimaryKeyService primaryKeyService;

    public ImtorheaderService(PrimaryKeyService primaryKeyService) {
        this.primaryKeyService = primaryKeyService;
    }


    public Optional<Imtorheader> getByZidAndXtornum(int zid, String xtornum) {
        return repository.findByZidAndXtornum(zid, xtornum);
    }


    public Imtorheader createDam(Imtorheader imtorheader) {
        String generatedKey=primaryKeyService.getGeneratedPrimaryKey(imtorheader.getZid(),"Transfer Transaction","DAM-",6);
        imtorheader.setXtornum(generatedKey);
        imtorheader.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        imtorheader.setZtime(LocalDateTime.now());
        imtorheader.setXstatustor("Open");
        return repository.save(imtorheader);
    }

    public Imtorheader updateByZidAndXtornum(Imtorheader imtorheader) {
        return repository.save(imtorheader);
    }

    public void deleteByZidAndXtornum(int zid, String xtornum) {

        repository.deleteByZidAndXtornum(zid, xtornum);
    }

    @Transactional
    public void deleteImtor(ImtorheaderId id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Cannot delete item due to foreign key constraints: " + ex.getMessage(), ex);
        }
    }

    public Page<ImtorDto> findImtorWithZidAndStatusAndUser(int zid, String xstatus, String user, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findImtorWithZidAndStatusAndUser(zid,xstatus,user,pageable);
    }

    public List<ImtorDto> searchByText(int zid, String searchText) {
        return repository.findGrnWithZidAndSearchText(zid,searchText);
    }



    public String confirmImtor(int zid, String user, String xposition,String xtornum, Date xdate, String xwh,String xtwh,String xstatustor,String xtype, int len) {
        try {
            String sql = "EXEC zabsp_confirmTO @zid = ?, @user = ?,@position=?, @tornum = ?, @date = ?, @fwh = ?,@twh=?,@statustor=?,@screen=?, @trnlength = ?";

            jdbcTemplate.update(sql, zid, user, xposition,xtornum, new java.sql.Date(xdate.getTime()), xwh,xtwh,xstatustor,xtype, len);

            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }
    }
}

