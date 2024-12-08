package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import com.zaberp.zab.biwtabackend.repository.ImtorheaderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ImtorheaderService extends CommonServiceImpl<Imtorheader, ImtorheaderId> {

    private final ImtorheaderRepository repository;
    private final PrimaryKeyService primaryKeyService;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public ImtorheaderService(ImtorheaderRepository repository, NamedParameterJdbcTemplate jdbcTemplate, PrimaryKeyService primaryKeyService) {
        super(jdbcTemplate);
        this.repository = repository;
        this.primaryKeyService = primaryKeyService;
    }


    public Imtorheader getSingleTrn(int zid,String xtornum){
        return repository.findByZidAndXtornum(zid,xtornum);
    }

    public Imtorheader createTransaction(Imtorheader imtorheader, String action) {
        String transactionPrefix = determineTransactionPrefix(action);
        String tempStatus = determineTempStatus(action);

        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(
                imtorheader.getZid(), "Transfer Transaction", transactionPrefix, 6
        );
        imtorheader.setXtornum(generatedKey);
        imtorheader.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        imtorheader.setZtime(LocalDateTime.now());
        imtorheader.setXstatussr("Open");
        imtorheader.setXtypesr("Transfer");
        imtorheader.setXstatustor(tempStatus);
        imtorheader.setXpreparer(imtorheader.getZauserid());
        imtorheader.setXtrn(transactionPrefix);
        return repository.save(imtorheader);
    }



    public Page<ImtorDto> findImtorWithFilters(int zid, String xstatus, String xtrn, String user, int page, int size, String sortBy, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findImtorWithZidAndStatusAndUser(zid, xstatus, xtrn, user, pageable);
    }



    public String confirmImtor(int zid, String user, String position, String xtornum, Date xdate, String xwh, String xtwh, String xstatustor, String screen, int len) {
        try {
            String sql = """
                        EXEC zabsp_Admin_confirmSR 
                        @zid = :zid, 
                        @user = :user, 
                      
                        @tornum = :xtornum, 
                        @datecom = :xdate, 
                        @fwh = :xwh, 
                        @twh = :xtwh, 
                        @status = :xstatustor, 
                     
                        @trnlength = :len
                    """;

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("zid", zid);
            parameters.addValue("user", user);
            parameters.addValue("position", position);
            parameters.addValue("xtornum", xtornum);
            parameters.addValue("xdate", new java.sql.Date(xdate.getTime()));
            parameters.addValue("xwh", xwh);
            parameters.addValue("xtwh", xtwh);
            parameters.addValue("xstatustor", xstatustor);
            parameters.addValue("screen", screen);
            parameters.addValue("len", len);

            // Execute the query
            namedParameterJdbcTemplate.update(sql, parameters);

            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }

    }
    public String checkSR(int zid, String user, String xtornum, Date xdate, String xfwh, String xstatustor) {
        try {
            String sql = """
            EXEC zabsp_IM_CheckMORequisition 
                @zid = :zid, 
                @user = :user, 
                @tornum = :xtornum, 
                @date = :xdate, 
                @wh = :xfwh, 
                @statustor = :xstatustor 
                """;
            MapSqlParameterSource parmeters = new MapSqlParameterSource();
            parmeters.addValue("zid",zid);
            parmeters.addValue("user",user);
            parmeters.addValue("xtornum",xtornum);
            parmeters.addValue("xdate",new java.sql.Date(xdate.getTime()));
            parmeters.addValue("xfwh",xfwh);
            parmeters.addValue("xstatustor",xstatustor);


            namedParameterJdbcTemplate.update(sql, parmeters);
            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }
    }


    private String determineTransactionPrefix(String action) {
        return switch (action.toLowerCase()) {
            case "damage" -> "DAM-";
            case "requisition" -> "SR--";
            default -> throw new IllegalArgumentException("Unsupported action: " + action);
        };
    }

    private String determineTempStatus(String action) {
        return "damage".equalsIgnoreCase(action) ? "Open" : "Open";
    }


    @Override
    public JpaRepository<Imtorheader, ImtorheaderId> getRepository() {

        return repository;
    }

    @Override
    public Page<Imtorheader> findByZidAndTrnnumWithPaginationAndSorting(int zid, String trnnum, int page, int size, String sortBy, boolean ascending) {
        return null;
    }



    @Override
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    protected String getTableName() {
        return "Imtorheader";
    }

    @Override
    protected RowMapper<Imtorheader> getRowMapper() {
        return new BeanPropertyRowMapper<>(Imtorheader.class);
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


    public Page<ImtorDto> callForImtor(int zid, String user, String superior, String status,String xtrn, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return getListImtor(zid,user,superior,status,xtrn,pageable);
    }

    public Page<ImtorDto> getListImtor(int zid, String user, String superior, String status,String xtrn, Pageable pageable) {
        System.out.println(xtrn);
        StringBuilder sql = new StringBuilder("SELECT new com.zaberp.zab.biwtabackend.dto.ImtorDto(" +
                "p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xstatustor, pd.xname) " +
                "FROM Imtorheader p " +
                "JOIN Pdmst pd ON p.zid = pd.zid AND p.xpreparer = pd.xstaff " +
                "JOIN Xcodes x ON p.zid = x.zid AND p.xfwh = x.xcode AND x.xtype = 'Branch' " +
                "WHERE p.zid = :zid and p.xtrn=:xtrn ");

        if (user != null && user!="") {
            sql.append(" AND p.zauserid = :user");
        }
        if (xtrn != null && xtrn!="") {
            sql.append(" AND p.xtrn = :xtrn");
        }
        if (superior != null && superior!="") {
            sql.append(" AND p.xidsup = :superior");
        }
        if (status != null && status!="") {
            sql.append(" AND p.xstatustor = :status");
        }
        System.out.println(sql.toString());
        TypedQuery<ImtorDto> query = entityManager.createQuery(sql.toString(), ImtorDto.class);
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
        if (xtrn != null && xtrn!="") {
            query.setParameter("xtrn", xtrn);
        }

        System.out.println(query.toString());
        // Implement pagination manually
        int totalRecords = query.getResultList().size();
        int firstResult = (int) pageable.getOffset();
        query.setFirstResult(firstResult);
        query.setMaxResults(pageable.getPageSize());
        List<ImtorDto> results = query.getResultList();
        return new PageImpl<>(results, pageable, totalRecords);
    }

}
