package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.repository.PogrnHeaderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
    public class PogrnHeaderService extends CommonServiceImpl<Pogrnheader,PogrnHeaderId>{

        @Autowired
        private PogrnHeaderRepository repository;

        @PersistenceContext
        private EntityManager entityManager;



        @Autowired
        private JdbcTemplate jdbcTemplate;

        private final PrimaryKeyService primaryKeyService;

        public PogrnHeaderService(PrimaryKeyService primaryKeyService) {
            this.primaryKeyService = primaryKeyService;
        }

        public Optional<Pogrnheader> getByZidAndXgrnnum(int zid, String xgrnnum) {
            return repository.findByZidAndXgrnnum(zid, xgrnnum);
        }


        public Pogrnheader createGrn(Pogrnheader pogrnheader) {
            String generatedKey=primaryKeyService.getGeneratedPrimaryKey(pogrnheader.getZid(),"GRN Number","GRN-",6);
            pogrnheader.setXgrnnum(generatedKey);
            pogrnheader.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
            pogrnheader.setZtime(LocalDateTime.now());
            pogrnheader.setXstatus("Open");
            pogrnheader.setXpreparer(pogrnheader.getZauserid());
            pogrnheader.setXstatusdoc("Open");
            pogrnheader.setXstatusgrn("Open");
            return repository.save(pogrnheader);
        }

        public Pogrnheader updateByZidAndXgrnnum(Pogrnheader entity) {
            return repository.save(entity);
        }

        public void deleteByZidAndXgrnnum(int zid, String xgrnnum) {

            repository.deleteByZidAndXgrnnum(zid, xgrnnum);
        }

        @Transactional
        public void deleteGrn(PogrnHeaderId id) {
            try {
                repository.deleteById(id);
            } catch (DataIntegrityViolationException ex) {
                throw new DataIntegrityViolationException("Cannot delete item due to foreign key constraints: " + ex.getMessage(), ex);
            }
        }

        public Page<PogrnheaderXcusdto> getPogrnList(int zid, String user, String superior, String status, int page, int size, String sortBy, boolean ascending) {
            Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            return getPogrnList(zid,user,superior,status,pageable);
        }


        public List<PogrnheaderXcusdto> searchByText(int zid, String searchText) {
            return repository.findGrnWithZidAndSearchText(zid,searchText);
        }



        public String confirmGRN(int zid, String user, String grnnum, Date date, String wh, int trnlength) {
            System.out.println("Executing procedure with params:");
            System.out.println("zid: " + zid + ", user: " + user + ", grnnum: " + grnnum + ", date: " + date + ", wh: " + wh + ", trnlength: " + trnlength);

            try {
                // Prepare the SQL query
                String sql = "EXEC zabsp_PO_confirmGRN @zid = ?, @user = ?, @grnnum = ?, @date = ?, @wh = ?, @trnlength = ?";

                // Execute the query with parameter binding
                jdbcTemplate.update(sql, zid, user, grnnum, new java.sql.Date(date.getTime()), wh, trnlength);

                return "Procedure executed successfully!";
            } catch (Exception e) {
                throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
            }
        }


    @Override
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public JpaRepository<Pogrnheader, PogrnHeaderId> getRepository() {
        return repository;
    }

    @Override
    protected String getTableName() {
        return "Pogrnheader";
    }

    @Override
    protected RowMapper<Pogrnheader> getRowMapper() {
        return new BeanPropertyRowMapper<>(Pogrnheader.class);
    }

    public Page<PogrnheaderXcusdto> getPogrnList(int zid, String user, String superior, String status, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT new com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto(" +
                "p.zid, p.xgrnnum, p.xdate, s.xcus, s.xorg, p.xwh, x.xlong, p.xstatus, p.xstatusdoc, p.zauserid, pd.xname) " +
                "FROM Pogrnheader p " +
                "JOIN Cacus s ON p.zid = s.zid AND p.xcus = s.xcus " +
                "JOIN Pdmst pd ON p.zid = pd.zid AND p.xpreparer = pd.xstaff " +
                "JOIN Xcodes x ON p.zid = x.zid AND p.xwh = x.xcode AND x.xtype = 'Branch' " +
                "WHERE p.zid = :zid");

        if (user != null && user!="") {
            sql.append(" AND p.zauserid = :user");
        }
        if (superior != null && superior!="") {
            sql.append(" AND p.xsuperiorsp = :superior");
        }
        if (status != null && status!="") {
            sql.append(" AND p.xstatusdoc = :status");
        }
        System.out.println(sql.toString());
        TypedQuery<PogrnheaderXcusdto> query = entityManager.createQuery(sql.toString(), PogrnheaderXcusdto.class);
        query.setParameter("zid", zid);
        if (user != null && user!="") {
            query.setParameter("user", user);
        }
        if (superior != null && superior!="") {
            query.setParameter("superior", superior);
        }
        if (status != null && status!="") {
            query.setParameter("status", status);
        }

        System.out.println(query.toString());
        // Implement pagination manually
        int totalRecords = query.getResultList().size();
        int firstResult = (int) pageable.getOffset();
        query.setFirstResult(firstResult);
        query.setMaxResults(pageable.getPageSize());

        List<PogrnheaderXcusdto> results = query.getResultList();
        return new PageImpl<>(results, pageable, totalRecords);
    }
}