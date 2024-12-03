package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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


    public boolean updateImtorheader(int zid, String xtornum, Map<String, Object> updates, List<String> excludeColumns) {
        int rowsUpdated = repository.updateExcludingColumns(zid, xtornum, updates, excludeColumns);
        return rowsUpdated > 0;
    }


    public Imtorheader createTransaction(Imtorheader imtorheader,String action) {
        String trn="";
        String tempStatus="";
        String xtrn = "";
        if ("damage".equalsIgnoreCase(action)){
            trn = "DAM-";
            tempStatus="Open";

        }
        else if ("Requisition".equalsIgnoreCase(action)){
            trn = "SR--";
            tempStatus="Approved";

        }
        String generatedKey=primaryKeyService.getGeneratedPrimaryKey(imtorheader.getZid(),"Transfer Transaction",trn,6);
        imtorheader.setXtornum(generatedKey);
        imtorheader.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        imtorheader.setZtime(LocalDateTime.now());
        imtorheader.setXstatustor(tempStatus);
        imtorheader.setXtrn(trn);
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

    public Page<ImtorDto> findImtorWithZidAndStatusAndUser(int zid, String xstatus,String xtrn, String user, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findImtorWithZidAndStatusAndUser(zid,xstatus,xtrn,user,pageable);
    }


    public Page<ImtorDto> findImtorWithZidAndStatus(int zid, String xtrn, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findImtorWithZidAndStatus(zid,xtrn,pageable);
    }

    public List<ImtorDto> searchByText(int zid,String action, String searchText) {
        return repository.findImtorWithZidAndSearchText(zid,action,searchText);
    }


    public List<ImtorDto> searchByText(int zid, String searchText) {
        return repository.findImtorWithZidAndText(zid,searchText);
    }



    public String confirmImtor(int zid, String user,String position,String xtornum, Date xdate, String xwh,String xtwh,String xstatustor,String screen, int len) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Format date for readability
        System.out.println("Parameters being passed:");
        System.out.println("zid: " + zid);
        System.out.println("user: " + user);
        System.out.println("position: " + position);
        System.out.println("xtornum: " + xtornum);
        System.out.println("xdate: " + sdf.format(xdate));
        System.out.println("xwh: " + xwh);
        System.out.println("xtwh: " + xtwh);
        System.out.println("xstatustor: " + xstatustor);
        System.out.println("screen: " + screen);
        System.out.println("len: " + len);
        try {
            String sql = "EXEC zabsp_confirmTO @zid = ?, @user = ?,@position=?, @tornum = ?, @date = ?, @fwh = ?,@twh=?,@statustor=?,@screen=?, @trnlength = ?";

            jdbcTemplate.update(sql, zid, user,position, xtornum, new java.sql.Date(xdate.getTime()), xwh,xtwh,xstatustor,"Transfer", len);

            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }
    }


    public String checkSR(int zid, String user,String xtornum, Date xdate, String xfwh,String xstatustor) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Parameters being passed:");
        System.out.println("zid: " + zid);
        System.out.println("user: " + user);
        System.out.println("xtornum: " + xtornum);
        System.out.println("xdate: " + sdf.format(xdate));
        System.out.println("xstatustor: " + xstatustor);
        try {
            String sql = "EXEC zabsp_IM_CheckMORequisition @zid = ?, @user = ?, @tornum = ?, @date = ?, @wh = ?,@statustor=?";

            jdbcTemplate.update(sql, zid, user, xtornum, new java.sql.Date(xdate.getTime()), xfwh,xstatustor);

            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }
    }
}

